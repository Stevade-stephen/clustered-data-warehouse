
# ProgressSoft Assessment

A data warehouse for analyzing FX deals and persist them into DB

## Requirements
To run this application, you will need docker installed. Additionally, you should have make installed also clone the repository.


## Run Locally via docker or make

Clone the project

```bash
  git clone https://github.com/Stevade-stephen/clustered-data-warehouse
```

Go to the project directory

```bash
  cd clustereddatawarehouse
```

Run Via Make

```bash
  make dev
```

Run Via Docker
```bash
  - docker compose build
  - docker compose up 
```


## API Reference
All Urls are relative to:
- for `docker` http://localhost:8081
- for running it via `terminal` http://localhost:8080
#### Save FX Deal

```http
  POST /api/v1/fx-deals/save
```

| Method   | HTTP Request            | Description                  |
|:---------|:------------------------|:-----------------------------|
| `POST`   | `/api/v1/fx-deals/save` | Endpoint for saving FX deals |

#### Sample Request
```
{
  "fromCurrency": "GBP",
  "toCurrency": "NGN",
  "dealAmountInOrderingCurrency": 10000.00,
  "dealUniqueId": "PQ-ytd99",
  "dealTimestamp": "2023-09-16T12:02:24.680682"
}
```
#### Sample Response
```
{
    "message": "FX Deal saved successfully",
    "status": "CREATED",
    "data": {
        "dealUniqueId": "PQ-ytd99",
        "fromCurrency": "GBP",
        "toCurrency": "NGN",
        "dealTimestamp": "2023-09-16T12:02:24.680682",
        "dealAmountInOrderingCurrency": 10000.00
    }

}

```


#### Get FX Deal

```http
  GET /api/v1/fx-deals/${id}
```

| Method   | HTTP Request             | Description                          |
|:---------|:-------------------------|:-------------------------------------|
| `GET`    | `/api/v1/fx-deals/${id}` | Endpoint to get a particular FX deal |

#### Sample Request
``` 
localhost:8080/api/v1/fx-deals/PQ-ytd99
```
#### Sample Response
```
{
    "message": "FX Deal retrieved successfully",
    "status": "FOUND",
    "data": {
        "dealUniqueId": "PQ-ytd99",
        "fromCurrencyISO": "GBP",
        "toCurrencyISO": "NGN",
        "dealTimestamp": "2023-09-16T04:22:24.946759",
        "amountInOrderingCurrency": 10000.00
    }
}
```


