#!/bin/sh
# Maven Wrapper script

set -e

SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
WRAPPER_PROPERTIES="$SCRIPT_DIR/.mvn/wrapper/maven-wrapper.properties"
MAVEN_HOME="$HOME/.m2/wrapper/dists"

# Read distributionUrl from maven-wrapper.properties
DIST_URL="https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip"
if [ -f "$WRAPPER_PROPERTIES" ]; then
    DIST_URL=$(grep 'distributionUrl' "$WRAPPER_PROPERTIES" | cut -d'=' -f2-)
fi

# Extract version name from URL  (e.g. apache-maven-3.9.6)
DIST_FILENAME=$(basename "$DIST_URL" .zip)
# The zip contains a folder named without the -bin suffix
DIST_NAME=$(echo "$DIST_FILENAME" | sed 's/-bin$//')

# Check if maven is already downloaded
if [ -x "$MAVEN_HOME/$DIST_NAME/bin/mvn" ]; then
    MVN_CMD="$MAVEN_HOME/$DIST_NAME/bin/mvn"
elif [ -x "$MAVEN_HOME/$DIST_FILENAME/$DIST_NAME/bin/mvn" ]; then
    MVN_CMD="$MAVEN_HOME/$DIST_FILENAME/$DIST_NAME/bin/mvn"
else
    echo "Downloading Maven from $DIST_URL..."
    mkdir -p "$MAVEN_HOME"
    TMPFILE=$(mktemp /tmp/maven-wrapper.XXXXXX.zip)
    curl -sL "$DIST_URL" -o "$TMPFILE"
    unzip -q "$TMPFILE" -d "$MAVEN_HOME"
    rm -f "$TMPFILE"

    if [ -x "$MAVEN_HOME/$DIST_NAME/bin/mvn" ]; then
        MVN_CMD="$MAVEN_HOME/$DIST_NAME/bin/mvn"
    else
        MVN_CMD="$MAVEN_HOME/$DIST_FILENAME/$DIST_NAME/bin/mvn"
    fi
fi

exec "$MVN_CMD" "$@"
