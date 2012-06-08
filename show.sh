#!/bin/bash

dot "$1" -Tpng -o "$1.png"
shotwell "$1.png"
