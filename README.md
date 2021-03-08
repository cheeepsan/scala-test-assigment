# Wolt assigment
## How to get this to work
1. You need Java same as or above version 8
    - in cmd do 'java --version', if it shows you version from java 8 (inclusive), you are good to go
    - if you have no java, install Oracle JDK or OpenJDK 8+ version

2. Install sbt: https://www.scala-sbt.org
3. Run sbt with command "sbt", once sbt shell is up - run project with "run" command
    - if sbt shell tells you that ports for webserver are in use - use "run %any port number", eg "run 9010"
4. To run tests use "test" command in the sbt shell context
## What is this about
Wolt wanted a programm that would parse JSON to readable plaintext format. 
Once programm is up, send a POST request to address: "localhost:%portnumber%/processTimetable"
## How to use
Example: <br/>
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
```

# Part 2
"Tell us what do you think about the data format. Is the current JSON structure the
best way to store that kind of data or can you come up with a better version?"<br/>

JSON structure was not as optimal as it could be, it is volatile in an essence and not too straightforward to use. <br/>

Overall structure would be much more simple if weekdays were not used as keys, weekday
should be a part of JSON object, not the key. <br/>

JSON would benefit a lot if data within JSON body was ordered, so developers could parse object
in any way they like and could preserve order later through the use of additional attributes.<br/>

Epoch as a time format itself is not bat, it is easy to use and parse. However, everyone would benefit
if epoch data contained correct date as well. Furthermore, weekday data could be omitted from body if epoch contained full info. <br/>

Array of open-closed data could be normalized. Personally I would prefer to change it to
boolean attribute, so we would have to use as little plaintext as possible.