curl -X POST -d '{"firstname":"Shirley","lastname":"Foster","dob":"06/09/1937","gender":"f","nickname":"Auntie","hobbies":"reading,draw,listening to music"}' -H content-type:application/json http://localhost:8080/api/todo/user

curl -X POST -d '{"firstname":"Daniel","lastname":"Ryckman","dob":"01/25/2006","gender":"m","nickname":"Dan","relationship":"grandson","hobbies":"soccer,building lego","description":"chinese teenager boy"}' -H content-type:application/json http://localhost:8080/api/todo/familymemberuser?userid=1

curl -X POST -d '{"firstname":"Robert","lastname":"Foster","dob":"07/04/1965","gender":"m","nickname":"Bob","relationship":"son","hobbies":"soccer,travel", "description":"white 45 years old man"}' -H content-type:application/json http://localhost:8080/api/todo/familymemberuser?userid=1


curl -X POST -d '{"firstname":"Madison","lastname":"Foster","dob":"07/04/1965","gender":"f","nickname":"Maddie","relationship":"daughter-in-law","hobbies":"soccer,travel", "description":"white 45 years old woman short brown air"}' -H content-type:application/json http://localhost:8080/api/todo/familymemberuser?userid=1

curl -X POST -d '{"firstname":"Elizabeth","lastname":"Foster","dob":"10/04/2003","gender":"f","nickname":"Lizzie","relationship":"granddaughter","hobbies":"dance,sing", "description":"white 17 years old teenager girl with longbrown hair"}' -H content-type:application/json http://localhost:8080/api/todo/familymemberuser?userid=1

curl -X POST -d '{"firstname":"William","lastname":"Greene","dob":"07/04/1965","gender":"m","nickname":"Bill","relationship":"nephew","hobbies":"soccer,travel", "description":"45 years old black man"}' -H content-type:application/json http://localhost:8080/api/todo/familymemberuser?userid=1

curl -X POST -d '{"firstname":"Amelia","lastname":"Greene","dob":"07/04/2021","gender":"f","nickname":"Mia","relationship":"grandniece","hobbies":"dance", "description":"black 1 year old girl"}' -H content-type:application/json http://localhost:8080/api/todo/familymemberuser?userid=1


curl -X POST -d '{"apptime":3,"rdate":16745221360,"phototime":2,"testtime":1,"testnumber":2,"averagescore":4,"commentnumber":2}' -H content-type:application/json http://localhost:8080/api/todo/record?userid=1

curl -X POST -d '{"apptime":12, "date":16757521360,"phototime":10,"testtime":2,"testnumber":1,"averagescore":2,"commentnumber":2}' -H content-type:application/json http://localhost:8080/api/todo/record?userid=1

curl -X POST -d '{"apptime":8, "rdate":16757563360,"phototime":6,"testtime":0,"testnumber":0,"averagescore":0,"commentnumber":2}' -H content-type:application/json http://localhost:8080/api/todo/record?userid=1

curl -X POST -d '{"apptime":26, "rdate":16757563361,"phototime":20,"testtime":0,"testnumber":0,"averagescore":0,"commentnumber":20}' -H content-type:application/json http://localhost:8080/api/todo/record?userid=1

curl -X POST -d '{"name":"taskLiz","description":"Elizabeth eating with friends","schedule":1674666876}' -H content-type:application/json http://localhost:8080/api/todo/taskuser?userid=1

curl -X POST -d '{"name":"taskDan","description":"Daniel is studying for exam in the library.","schedule":1674646876}' -H content-type:application/json http://localhost:8080/api/todo/taskuser?userid=1

curl -X POST -d '{"name":"taskMadison","description":"Madison is baking a cake","schedule":1674646876}' -H content-type:application/json http://localhost:8080/api/todo/taskuser?userid=1

curl -X POST -d '{"name":"taskMia","description":"Amelia is having a birthday party in the park","schedule":1674646876}' -H content-type:application/json http://localhost:8080/api/todo/taskuser?userid=1

curl -X POST -d '{"name":"taskWilliam","description":"William is going to grocery shopping","schedule":1674646876}' -H content-type:application/json http://localhost:8080/api/todo/taskuser?userid=1
