#!/bin/bash

createdb scoreboard
createuser scoreboard_app
#psql scoreboard -f ./db/initial_setup.sql
