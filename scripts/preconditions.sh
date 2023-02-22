#!/bin/bash

#
# Checks some host OS preconditions (e.g. available commands/binaries) needed to run tuner scripts.
#

SCRIPT_DIR="$(dirname "${BASH_SOURCE[0]}")"
source "$SCRIPT_DIR/common/common.sh"

P_TAG="preconditions"

main() {
  check_os
  check_symlinks_resolver
  check_java
}

check_os() {
  if [[ "$OSTYPE" != "darwin"* && "$OSTYPE" != "linux"* ]]; then
    print_error $P_TAG "tuner is available only on Linux and macOS"
    exit 1
  fi
}

check_symlinks_resolver() {
  if [[ "$OSTYPE" == "darwin"* ]] && ! command -v perl >/dev/null 2>&1; then
    print_error $P_TAG "you must have 'perl' installed to resolve symlinks on macOS"
    exit 1
  else
    print_success $P_TAG "symlinks resolver available"
  fi
}

check_java() {
  if [[ -z "$JAVA_HOME" ]]; then
    print_error $P_TAG "You need to set your JAVA_HOME"
    exit 1
  else
    print_success $P_TAG "JAVA_HOME set correctly"
  fi
}

main
