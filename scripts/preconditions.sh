#!/bin/bash

#
# Checks some host OS preconditions (e.g. available commands/binaries) needed to run tuner scripts.
#

SCRIPT_DIR="$(dirname "${BASH_SOURCE[0]}")"
source "$SCRIPT_DIR/common/common.sh"

P_TAG="preconditions"

main() {
  check_symlinks_resolver
}

check_symlinks_resolver() {
  if [[ "$OSTYPE" == "darwin"* ]] && ! command -v perl >/dev/null 2>&1; then
    print_error $P_TAG "you must have 'perl' installed to resolve symlinks on macOS"
    exit 1
  else
    print_success $P_TAG "symlinks resolver available"
  fi
}

main
