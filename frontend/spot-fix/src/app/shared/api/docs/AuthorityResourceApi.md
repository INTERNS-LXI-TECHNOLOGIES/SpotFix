# AuthorityResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createAuthority**](AuthorityResourceApi.md#createauthority) | **POST** /api/authorities |  |
| [**deleteAuthority**](AuthorityResourceApi.md#deleteauthority) | **DELETE** /api/authorities/{id} |  |
| [**getAllAuthoritiesAsStream**](AuthorityResourceApi.md#getallauthoritiesasstream) | **GET** /api/authorities |  |
| [**getAuthority**](AuthorityResourceApi.md#getauthority) | **GET** /api/authorities/{id} |  |



## createAuthority

> Authority createAuthority(authority)



### Example

```ts
import {
  Configuration,
  AuthorityResourceApi,
} from '';
import type { CreateAuthorityRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AuthorityResourceApi();

  const body = {
    // Authority
    authority: ...,
  } satisfies CreateAuthorityRequest;

  try {
    const data = await api.createAuthority(body);
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
| **authority** | [Authority](Authority.md) |  | |

### Return type

[**Authority**](Authority.md)

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


## deleteAuthority

> deleteAuthority(id)



### Example

```ts
import {
  Configuration,
  AuthorityResourceApi,
} from '';
import type { DeleteAuthorityRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AuthorityResourceApi();

  const body = {
    // string
    id: id_example,
  } satisfies DeleteAuthorityRequest;

  try {
    const data = await api.deleteAuthority(body);
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
| **id** | `string` |  | [Defaults to `undefined`] |

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


## getAllAuthoritiesAsStream

> Array&lt;Authority&gt; getAllAuthoritiesAsStream()



### Example

```ts
import {
  Configuration,
  AuthorityResourceApi,
} from '';
import type { GetAllAuthoritiesAsStreamRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AuthorityResourceApi();

  try {
    const data = await api.getAllAuthoritiesAsStream();
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

[**Array&lt;Authority&gt;**](Authority.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `application/x-ndjson`, `application/json`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getAuthority

> Authority getAuthority(id)



### Example

```ts
import {
  Configuration,
  AuthorityResourceApi,
} from '';
import type { GetAuthorityRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AuthorityResourceApi();

  const body = {
    // string
    id: id_example,
  } satisfies GetAuthorityRequest;

  try {
    const data = await api.getAuthority(body);
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
| **id** | `string` |  | [Defaults to `undefined`] |

### Return type

[**Authority**](Authority.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)

