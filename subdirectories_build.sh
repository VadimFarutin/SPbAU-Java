#!/bin/bash

set -ev

build_subdirectory () {
  while read name;
  do
    #printf "%s\n" "$name"

    cd $name

    if [ -f "gradlew" ]
    then
      chmod +x gradlew
      ./gradlew build
    fi

    cd ..
  done

  exit
}

find -maxdepth 2 -mindepth 2 -type d | build_subdirectory
