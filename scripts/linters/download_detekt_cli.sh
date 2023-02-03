#!/bin/bash

#
# Downloads the necessary Detekt jars to perform checks without invoking Gradle.
# This script will output in `detekt/bin/` which is excluded in our .gitignore.
# The output is cached and will be invalidated only upon version changes.
# To force the invalidation you can invoke this script with the `force` argument:
# `./scripts/detekt/download_detekt_cli.sh force`
#

SCRIPT_DIR="$(dirname "${BASH_SOURCE[0]}")"
source "$SCRIPT_DIR/../common/common.sh"

P_TAG="download-detekt-cli"

DETEKT_BIN_DIR="$SCRIPT_DIR/../../detekt/bin"
DETEKT_CLI_JAR="$DETEKT_BIN_DIR/detekt_cli.jar"
DETEKT_TWITTER_COMPOSE_JAR="$DETEKT_BIN_DIR/detekt_twitter_compose.jar"
DETEKT_TWITTER_COMPOSE_VERSION_FILE="$DETEKT_BIN_DIR/.detekt_twitter_compose_version"

main() {
  # TODO read from version catalog
  local new_detekt_version="1.22.0"
  # TODO read from version catalog
  local new_detekt_twitter_compose_version="0.0.26"

  local force_bump="$1"
  if [[ $force_bump == "force" ]]; then
    bump_detekt $new_detekt_version
    bump_detekt_twitter_compose $new_detekt_twitter_compose_version
    exit 0
  fi

  if [[ -f "$DETEKT_CLI_JAR" ]]; then
    current_detekt_version=$(java -jar "$DETEKT_CLI_JAR" --version)
  fi
  if [[ "$new_detekt_version" == "$current_detekt_version" ]]; then
    print_info $P_TAG "detekt-cli $new_detekt_version already downloaded, skipping"
  else
    bump_detekt $new_detekt_version
  fi

  if [[ -f "$DETEKT_TWITTER_COMPOSE_JAR" && -f "$DETEKT_TWITTER_COMPOSE_VERSION_FILE" ]]; then
    read -r current_detekt_twitter_compose_version <"$DETEKT_TWITTER_COMPOSE_VERSION_FILE"
  fi
  if [[ "$new_detekt_twitter_compose_version" == "$current_detekt_twitter_compose_version" ]]; then
    print_info $P_TAG "detekt-twitter-compose $new_detekt_twitter_compose_version already downloaded, skipping"
  else
    bump_detekt_twitter_compose $new_detekt_twitter_compose_version
  fi
}

bump_detekt() {
  detekt_version=$1

  mkdir -p "$DETEKT_BIN_DIR"

  print_info $P_TAG "downloading detekt-cli $detekt_version"
  local detekt_cli_url="https://github.com/detekt/detekt/releases/download/v$detekt_version/detekt-cli-$detekt_version-all.jar"
  download_file "$detekt_cli_url" "$DETEKT_CLI_JAR"
  print_success $P_TAG "detekt-cli $detekt_version successfully downloaded"
}

bump_detekt_twitter_compose() {
  detekt_twitter_compose_version=$1

  mkdir -p "$DETEKT_BIN_DIR"

  print_info $P_TAG "downloading detekt-twitter-compose $detekt_twitter_compose_version"
  local detekt_twitter_compose_url="https://github.com/twitter/compose-rules/releases/download/v$detekt_twitter_compose_version/detekt-twitter-compose-$detekt_twitter_compose_version-all.jar"
  download_file "$detekt_twitter_compose_url" "$DETEKT_TWITTER_COMPOSE_JAR"
  echo "$detekt_twitter_compose_version" >"$DETEKT_TWITTER_COMPOSE_VERSION_FILE"
  print_success $P_TAG "detekt-twitter-compose $detekt_twitter_compose_version successfully downloaded"
}

download_file() {
  local url=$1
  local output=$2
  if command -v curl >/dev/null 2>&1; then
    curl -L "$url" -o "$output"
  elif command -v wget >/dev/null 2>&1; then
    wget "$url" -O "$output"
  else
    print_error $P_TAG "no 'curl' or 'wget' installed"
    exit 1
  fi
}

main "$@"
