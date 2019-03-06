# Side-B

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c23e52d4a8fc401384b379cd7b5ac630)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=I-am-Miguel/side-b&amp;utm_campaign=Badge_Grade)

## Poposta
Criar um conjunto de serviços capaz de aceitar requisições RESTful recebendo como parâmetro o nome da cidade ou as coordenadas(lat, long) e retorna como sugestão uma playList de acordo com a temperatura atual.

APIs Externas:
* [OpenWeatherMaps](https://openweathermap.org) 
* [Spotify](https://developer.spotify.com)

Foram criados os seguintes Serviços:
* `Cloud-config-server`
* `Eureka-service`
* `Music-service`
* `Weather-service`
* `Sugestion-service`
* `Zull-server`

### Obtendo o Projeto
Os passos para obter o projeto em execução são os seguintes:

```
$ git clone https://github.com/I-am-Miguel/side-b.git sideb_miguel
$ cd sideb_miguel
$ ls
loud-config-server   LICENSE        Side-b.svg         zuul-server
eureka-service        music-service  sugestion-service
README.md      weather-service
$ mkdir git-localconfig-repo && cd git-localconfig-repo
git-localconfig-repo$ touch music-service.properties  
git-localconfig-repo$ touch weather-service.properties
```
Após criado os arquivos `.properties` de cada serviço, será necessário adicionar as chaves de acesso de cada api sendo elas:
```
#music-service.properties
music-service.hostToken=https://accounts.spotify.com/api/token
music-service.hostTrack=https://api.spotify.com/v1/search?q={genre}&type=track
music-service.clientId=<clientID>
music-service.clientSecret=<clientSecret>
```
```
#weather-service.properties
weather-service.city=http://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}
weather-service.coordinates=http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lng}&appid={key}
weather-service.key=<KEY>
```

### Cloud-config-server
Responsável por centralizar as informações de cada serviço, através dele é gerenciado o `SPOTIFY_TOKEN`, `WEATHER_KEY` e demais chaves de acessos pessoais que não deveriam ser versionadas.

Execução:
```
$ cd cloud-config-server
$ gradle build
```

|     Aplicação       |     Porta          |
| ------------- | ------------- |
| Config Server | 8888 |




### Eureka-service
Responsável pelo `Service Registry`, realizando assim o dimensionamento dinâmico.

Execução:
```
$ cd eureka-service
$ gradle build
```

|     Aplicação       |     Porta          |
| ------------- | ------------- |
| Eureka Service | 8761 |

### Music-service
Responsável por se comunicar com o [Spotify](https://developer.spotify.com) retornando uma playList baseada nos gêneros predefinidos.

Execução:
```
$ cd music-service
$ gradle build
```

|     Aplicação       |     Porta          |
| ------------- | ------------- |
| Music Service | 8100, 8101, ... |


Exemplo de requisição:
CUrl :`curl -X GET "http://localhost:8100/genre/pop" -H "accept: application/json"`
```
{
"tracks": {
"href": "https://api.spotify.com/v1/search?query=pop&type=track&offset=0&limit=20",
"items": [
  {
    "name": "Pop Out (feat. Lil TJay)"
  },
  {
    "name": "POP/STARS"
  },
  {
    "name": "pop a pill"
  }
  ...
  ]
}
```
UI: http://localhost:8100/swagger-ui.html


### Weather-service
Responsável por se comunicar com o [OpenWeatherMaps](https://openweathermap.org) retornando os dados climáticos do local pesquisado, seja por nome ou coordenadas.
obs: Esse serviço representou o maior gargalo da arquitetura, cada requisição a esta API levavam em média 14000ms, por esse motivo foi utilizado o banco [Redis](https://redis.io/documentation) para armazenar essa informação em cache

Execução:
```
$ cd weather-service
$ gradle build
```

|     Aplicação       |     Porta          |
| ------------- | ------------- |
| Weather Service | 8000, 8001, ... |

Exemplo de requisição:
CUrl :`curl -X GET "http://localhost:8000/city/santos" -H "accept: application/json"`
```
{
  "id": "3449433",
  "lat": "-23.96",
  "lon": "-46.33",
  "dt": "1551883509",
  "cod": "200",
  "visibility": "10000",
  "name": "SANTOS",
  "base": "stations",
  "weather": [
    {
      "id": "800",
      "icon": "01d",
      "description": "clear sky",
      "main": "Clear"
    }
  ],
  "main": {
    "temp": "301.08",
    "pressure": "1022",
    "humidity": "58",
    "temp_min": "300.93",
    "temp_max": "301.15"
  },
  "sys": {
    "country": "BR"
  },
  "coord": {
    "lat": "-23.96",
    "lon": "-46.33"
  },
  "tempCelcius": 27.93
}
```
UI: http://localhost:8000/swagger-ui.html



### Sugestion-service
Agregação dos serviços listados anteriormente, nele foi possível unir as informações de cada serviço, além de atuar como um load balancer e possuir métricas de tolerância a falhas.
Execução:
```
$ cd sugestion-service
$ gradle build
```

|     Aplicação       |     Porta          |
| ------------- | ------------- |
| Sugestion Service | 8200, 8201, ... |

Exemplo de requisição:
CUrl :`curl -X GET "http://localhost:8200/sugestion/city/santos" -H "accept: application/json"`
```
{
  "message": "It is a little warm out there, what do you think of the playList to cheer up. Temperature: 27.93",
  "results": [
    "Pop Out (feat. Lil TJay)",
    "POP/STARS",
    "pop a pill",
    "Pop That",
    "Pop Style",
    "Pop Star",
    "Pop Up",
    "SexyBack (feat. Timbaland)",
    "OutRight",
    "Pop Style",
    "Love Scars Pt. 2 / Rack City",
    "This Is How We Party (with Icona Pop)",
    "Pop",
    "Is She Gon Pop",
    "Blue (Da Ba Dee) - Gabry Ponte Ice Pop Radio",
    "Buttercup",
    "Pop The Trunk",
    "Oh Klahoma",
    "Cups (Pitch Perfect’s “When I’m Gone”) - Pop Version",
    "Pop Style"
  ]
}
```
UI: http://localhost:8200/swagger-ui.html

# Tolerância a falhas
Ao lidar com vários serviços é necessário garantir que nosso sistema possui tolerância a falhas, caso um serviço fique fora do ar(Serviços sempre falham :P), vejamos o que acontece caso o serviço que se comunica com o spotify fique indisponível:

CUrl :`curl -X GET "http://localhost:8200/sugestion/city/santos" -H "accept: application/json"`
```
{
  "message": "One of our services is currently unavailable, we are very sorry, please enjoy our playlists",
  "results": [
    "Só Quero Ver Voce / There Is Only One - Ao Vivo",
    "Isaias 9",
    "O Dia Que Será Pra Sempre",
    "Isaías 9 - Ao Vivo",
    "Até Que A Casa Esteja Cheia",
    "Pisaduras - Ao Vivo",
    "Dia Quente",
    "Na Gravidade da Presença",
    "No Teu Jardim - Ao Vivo",
    "Isaías 9 - Ao Vivo",
    "Nível Raso",
    "Minha Maior Riqueza",
    "Todo Dia Até Morrer",
    "Saudades de Casa",
    "Tão Sublime - Ao Vivo",
    "Mar de Vidro - Ao Vivo",
    "Na Gravidade da Presença",
    "Avançar (feat. Rodolfo Abrantes)",
    "Parecido Contigo",
    "Uma Luz Que Não Pode Se Apagar"
  ]
}
```


### Zull-server
Fronteira de acesso da API, o mesmo funciona como proxy de acesso `Bypass` delegando as requisições para o Sugestion-service
```
$ cd zuul-server
$ gradle build
```

|     Aplicação       |     Porta          |
| ------------- | ------------- |
| Zull Service | 8080 |

byPass `localhost:8080 -> localhost:8200/swagger-ui.html`

A imagem a seguir ilusta a comunicação entre cada serviço da arquitetura:

### Design

![Arquitetura Side-B](https://github.com/I-am-Miguel/side-b/blob/master/Side-b.svg)
