const API_URL = '/ktp';

// ============ Notification ============
function showNotification(message, type) {
    const $notif = $('#notification');
    const $msg = $('#notification-message');
    $notif.removeClass('hidden success error warning').addClass(type);
    $msg.text(message);

    // Auto-hide after 4 seconds
    clearTimeout(window._notifTimeout);
    window._notifTimeout = setTimeout(hideNotification, 4000);
}

function hideNotification() {
    $('#notification').addClass('hidden');
}

// ============ Load Data ============
function loadData() {
    $.ajax({
        url: API_URL,
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            renderTable(response.data);
        },
        error: function () {
            showNotification('Gagal memuat data dari server', 'error');
        }
    });
}

// ============ Render Table ============
function renderTable(data) {
    const $tbody = $('#ktp-tbody');
    $tbody.empty();

    if (!data || data.length === 0) {
        $tbody.append('<tr><td colspan="7" class="empty-state">Belum ada data KTP</td></tr>');
        return;
    }

    data.forEach(function (ktp, index) {
        const row = `
            <tr>
                <td>${index + 1}</td>
                <td>${escapeHtml(ktp.nomorKtp)}</td>
                <td>${escapeHtml(ktp.namaLengkap)}</td>
                <td>${escapeHtml(ktp.alamat)}</td>
                <td>${formatDate(ktp.tanggalLahir)}</td>
                <td>${escapeHtml(ktp.jenisKelamin)}</td>
                <td class="action-btns">
                    <button class="btn btn-edit" onclick="editKtp(${ktp.id})">Edit</button>
                    <button class="btn btn-delete" onclick="deleteKtp(${ktp.id})">Hapus</button>
                </td>
            </tr>
        `;
        $tbody.append(row);
    });
}

// ============ Client-side Validation ============
function validateForm(ktpData) {
    if (!ktpData.nomorKtp || !ktpData.namaLengkap || !ktpData.alamat || !ktpData.tanggalLahir || !ktpData.jenisKelamin) {
        showNotification('Semua field harus diisi', 'warning');
        return false;
    }

    // Nomor KTP harus 16 digit angka
    if (!/^[0-9]{16}$/.test(ktpData.nomorKtp)) {
        showNotification('Nomor KTP harus berupa 16 digit angka', 'warning');
        return false;
    }

    // Nama lengkap minimal 3 karakter
    if (ktpData.namaLengkap.length < 3) {
        showNotification('Nama lengkap minimal 3 karakter', 'warning');
        return false;
    }

    // Alamat minimal 5 karakter
    if (ktpData.alamat.length < 5) {
        showNotification('Alamat minimal 5 karakter', 'warning');
        return false;
    }

    // Jenis kelamin harus valid
    if (ktpData.jenisKelamin !== 'Laki-laki' && ktpData.jenisKelamin !== 'Perempuan') {
        showNotification('Jenis kelamin harus Laki-laki atau Perempuan', 'warning');
        return false;
    }

    return true;
}

// ============ Create / Update ============
$('#ktp-form').on('submit', function (e) {
    e.preventDefault();

    const id = $('#ktp-id').val();
    const ktpData = {
        nomorKtp: $('#nomorKtp').val().trim(),
        namaLengkap: $('#namaLengkap').val().trim(),
        alamat: $('#alamat').val().trim(),
        tanggalLahir: $('#tanggalLahir').val(),
        jenisKelamin: $('#jenisKelamin').val()
    };

    // Client-side validation
    if (!validateForm(ktpData)) {
        return;
    }

    if (id) {
        // Update
        $.ajax({
            url: API_URL + '/' + id,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(ktpData),
            success: function (response) {
                showNotification(response.message || 'Data KTP berhasil diperbarui', 'success');
                resetForm();
                loadData();
            },
            error: function (xhr) {
                handleError(xhr, 'memperbarui');
            }
        });
    } else {
        // Create
        $.ajax({
            url: API_URL,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(ktpData),
            success: function (response) {
                showNotification(response.message || 'Data KTP berhasil ditambahkan', 'success');
                resetForm();
                loadData();
            },
            error: function (xhr) {
                handleError(xhr, 'menambahkan');
            }
        });
    }
});

// ============ Edit ============
function editKtp(id) {
    $.ajax({
        url: API_URL + '/' + id,
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            const ktp = response.data;
            $('#ktp-id').val(ktp.id);
            $('#nomorKtp').val(ktp.nomorKtp);
            $('#namaLengkap').val(ktp.namaLengkap);
            $('#alamat').val(ktp.alamat);
            $('#tanggalLahir').val(ktp.tanggalLahir);
            $('#jenisKelamin').val(ktp.jenisKelamin);

            $('#form-title').text('Edit Data KTP');
            $('#btn-submit').text('Perbarui Data');
            $('#btn-cancel').show();

            // Scroll to form
            $('html, body').animate({ scrollTop: 0 }, 300);
        },
        error: function (xhr) {
            handleError(xhr, 'mengambil');
        }
    });
}

// ============ Delete ============
function deleteKtp(id) {
    if (!confirm('Apakah Anda yakin ingin menghapus data KTP ini?')) {
        return;
    }

    $.ajax({
        url: API_URL + '/' + id,
        type: 'DELETE',
        success: function (response) {
            showNotification(response.message || 'Data KTP berhasil dihapus', 'success');
            loadData();

            // If currently editing this record, reset form
            if ($('#ktp-id').val() == id) {
                resetForm();
            }
        },
        error: function (xhr) {
            handleError(xhr, 'menghapus');
        }
    });
}

// ============ Reset Form ============
function resetForm() {
    $('#ktp-form')[0].reset();
    $('#ktp-id').val('');
    $('#form-title').text('Tambah Data KTP');
    $('#btn-submit').text('Simpan Data');
    $('#btn-cancel').hide();
}

// ============ Error Handler ============
function handleError(xhr, action) {
    let message = 'Gagal ' + action + ' data KTP';
    try {
        const response = JSON.parse(xhr.responseText);

        if (response.details) {
            // Validation errors from server (400)
            const errors = Object.values(response.details);
            message = errors.join(', ');
        } else if (response.message) {
            // Other error messages (404, 409, etc.)
            message = response.message;
        }
    } catch (e) {
        // use default message
    }
    showNotification(message, 'error');
}

// ============ Utilities ============
function escapeHtml(text) {
    if (!text) return '';
    const map = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;' };
    return text.replace(/[&<>"']/g, function (m) { return map[m]; });
}

function formatDate(dateStr) {
    if (!dateStr) return '-';
    const parts = dateStr.split('-');
    if (parts.length !== 3) return dateStr;
    return parts[2] + '-' + parts[1] + '-' + parts[0]; // DD-MM-YYYY
}

// ============ Init ============
$(document).ready(function () {
    loadData();
});
