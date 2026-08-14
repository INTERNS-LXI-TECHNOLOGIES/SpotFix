# AuthenticateControllerApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**authorize**](AuthenticateControllerApi.md#authorize) | **POST** /api/authenticate |  |
| [**isAuthenticated**](AuthenticateControllerApi.md#isauthenticated) | **GET** /api/authenticate |  |



## authorize

> JWTToken authorize(loginVM)



### Example

```ts
import {
  Configuration,
  AuthenticateControllerApi,
} from '';
import type { AuthorizeRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AuthenticateControllerApi();

  const body = {
    // LoginVM
    loginVM: ...,
  } satisfies AuthorizeRequest;

  try {
    const data = await api.authorize(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **loginVM** | [LoginVM](LoginVM.md) |  | |

### Return type

[**JWTToken**](JWTToken.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## isAuthenticated

> isAuthenticated()



### Example

```ts
import {
  Configuration,
  AuthenticateControllerApi,
} from '';
import type { IsAuthenticatedRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AuthenticateControllerApi();

  try {
    const data = await api.isAuthenticated();
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters

This endpoint does not need any parameter.

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)

