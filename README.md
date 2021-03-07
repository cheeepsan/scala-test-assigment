#Wolt assigment
## How to get this to work
1. You need Java same as or above version 8
    - in cmd do 'java --version', if it shows you version from java 8 (inclusive), you are good to go
    - if you have no java, install Oracle JDK or OpenJDK 8+ version

2. Install sbt: https://www.scala-sbt.org
3. run sbt with command "sbt", once sbt shell is up - run project with "run" command
    - if sbt shell tells you that ports for webserver are in use - use "run %any port number", eg "run 9010"
## What is this about
Wolt wanted a programm that would parse JSON to readable plaintext format. 
Once programm is up, send a POST request to address: "localhost:%portnumber%/processTimetable"
## How to use
Example:
POST localhost:9000/processTimetable <br/>
HEADERS: "Content-type: application/json"<br/>
PAYLOAD:
```json
{
    "monday": [],
    "tuesday": [
        {
            "type": "open",
            "value": 36000
        },
        {
            "type": "close",
            "value": 64800
        }
    ],
    "wednesday": [],
    "thursday": [
        {
            "type": "open",
            "value": 36000
        },
        {
            "type": "close",
            "value": 64800
        }
    ],
    "friday": [
        {
            "type": "open",
            "value": 36000
        }
    ],
    "saturday": [
        {
            "type": "close",
            "value": 3600
        },
        {
            "type": "open",
            "value": 36000
        }
    ],
    "sunday": [
        {
            "type": "close",
            "value": 3600
        },
        {
            "type": "open",
            "value": 43200
        },
        {
            "type": "close",
            "value": 75600
        }
  ]
}