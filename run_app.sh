#!/bin/bash

usage() {
    echo "usage: ./run_app --is <issuerUrl> --nu <true | false> --cp <true>"
    }

[ $? -eq 0 ] || {
    echo "Three command-line parameters are required"
    exit 1
    }

while test -n "$1"; do
    case "$1" in
    --is)
        ISSUER=$2
        shift 2
        ;;
    --nu)
        CREATE_USER=$2
        shift 2
        ;;
    --cp)
        CHANGE_PASSWORD=$2
        shift 2
        ;;
    esac
done

[ -z "$ISSUER" ] && usage && echo "--is is required" && exit 1
[ -z "$CREATE_USER" ] && usage && echo "--nu is required" && exit 1
[ -z "CHANGE_PASSWORD" ] && usage && echo "--cp is required" && exit 1

mvn exec:java \
    -Dexec.mainClass=org.redlich.okta.Application \
    -Dexec.args="$ISSUER $CREATE_USER $CHANGE_PASSWORD"
