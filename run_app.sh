#!/bin/bash

usage() {
    echo "usage: ./run_app --is <issuerURL> --cr <true | false>
    }

[ $? -eq 0 ] || {
    echo "Incorrect options provided"
    exit 1
    }

while test -n "$1"; do
    case "$1" in
    --is)
        CLIENT_ID=$2
        shift 2
        ;;
    --cr)
        CLIENT_SECRET=$2
        shift 2
        ;;

    esac
done

[ -z "$ISSUER" ] && usage && echo "--is is required" && exit 1
[ -z "$CREATE" ] && usage && echo "--cr is required" && exit 1

mvn exec:java -Dexec.mainClass=org.redlich.okta.Application -Dexec.args="$ISSUER $CREATE"
