#!/bin/bash
set -e

current_dir="$(pwd)"
jmeter -q "$current_dir/application.properties" -t ./load-testing.jmx