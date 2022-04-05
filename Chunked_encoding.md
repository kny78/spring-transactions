Chunked Encoding
================

Formål med Chunked Encoding
---------------------------

Http krever i utgangspunktet at "Content-length" headeren er satt.
For Client side, så skal man kunna lesa "content_length" antall bytes, og så har ein lest det som kommer.

Men for streaming av data med ukjent lengde, så veit vi ikkje lengden på body.
Då er det to alternativer:
* Cache opp heile body'en, og finna lengden, og så senda Content-length.
* Senda ut "chunks" av bodyen, med ein kortare lengde. Då trenger vi bare å "cache" opp ein chunk.

Eksempel på enkel HTTP
----------------------

### Frå client:
CURL: `curl -s http://localhost:18081/mydata/other -D - --raw`
```
GET /mydata/other HTTP/1.1
Host: localhost:18081
User-Agent: curl/7.82.0
Accept: */*

```

### Server response:
```
HTTP/1.1 404 Not Found
Content-Type: application/json
Content-Type: application/json
Content-Length: 136

{"timestamp":"2022-04-05T10:16:33.970+00:00","path":"/ugyldig","status":404,"error":"Not Found","message":null,"requestId":"5731f093-1"}
```


Eksempel med response-body chunked
----------------------------------

Response-body er body'en frå serveren.

Typisk svarar då server med:

```
HTTP/1.1 200 OK
transfer-encoding: chunked
Accept-Ranges: bytes
Content-Type: application/octet-stream

1000
0,Mr 0,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø
1,Mr 1,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø
2,Mr 2,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø
...MASSE DATA...
39,Mr 39,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsa
1000
dø
40,Mr 40,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø
41,Mr 41,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø
...MASSE DATA...
79,Mr 79,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsøl
1000
kfsdjaløkfsajlfkasdjfsadø
80,Mr 80,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø
...MASSE DATA...
10000,Mr 10000,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø

0

```



Eksempel med request-body chunked
---------------------------------

Client curl: 
`cat /home/kny/Downloads/dump.txt |head -n 30|  curl -XPOST -H "Transfer-encoding: chunked" -s http://localhost:18081/mydata/other -D - --raw  --limit-rate 1k -d @-`

lytte-server:
`nc -l 18081`

### Client HTTP
```
nc -l 18081
POST /mydata/other HTTP/1.1
Host: localhost:18081
User-Agent: curl/7.82.0
Accept: */*
Transfer-encoding: chunked
Content-Type: application/x-www-form-urlencoded

be0
0,Mr 0,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø1,Mr 1,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø2,Mr 2,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø3,Mr 3,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø4,Mr 4,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø5,Mr 5,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø6,Mr 6,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø7,Mr 7,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø8,Mr 8,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø9,Mr 9,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø10,Mr 10,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø11,Mr 11,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø12,Mr 12,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø13,Mr 13,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø14,Mr 14,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø15,Mr 15,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø16,Mr 16,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø17,Mr 17,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø18,Mr 18,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø19,Mr 19,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø20,Mr 20,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø21,Mr 21,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø22,Mr 22,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø23,Mr 23,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø24,Mr 24,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø25,Mr 25,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø26,Mr 26,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø27,Mr 27,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø28,Mr 28,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø29,Mr 29,fsaføajsfløkdsajflkdsajfølkdajfløkdsjflkajdsføljsadløfdjsølkfsdjaløkfsajlfkasdjfsadø
0

```