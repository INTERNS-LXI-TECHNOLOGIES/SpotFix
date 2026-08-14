# UserResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createUser**](UserResourceApi.md#createuser) | **POST** /api/admin/users |  |
| [**deleteUser**](UserResourceApi.md#deleteuser) | **DELETE** /api/admin/users/{login} |  |
| [**getAllUsers**](UserResourceApi.md#getallusers) | **GET** /api/admin/users |  |
| [**getUser**](UserResourceApi.md#getuser) | **GET** /api/admin/users/{login} |  |
| [**updateUser**](UserResourceApi.md#updateuser) | **PUT** /api/admin/users |  |
| [**updateUser1**](UserResourceApi.md#updateuser1) | **PUT** /api/admin/users/{login} |  |



## createUser

> User createUser(adminUserDTO)



### Example

```ts
import {
  Configuration,
  UserResourceApi,
} from '';
import type { CreateUserRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserResourceApi();

  const body = {
    // AdminUserDTO
    adminUserDTO: ...,
  } satisfies CreateUserRequest;

  try {
    const data = await api.createUser(body);
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
| **adminUserDTO** | [AdminUserDTO](AdminUserDTO.md) |  | |

### Return type

[**User**](User.md)

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


## deleteUser

> deleteUser(login)



### Example

```ts
import {
  Configuration,
  UserResourceApi,
} from '';
import type { DeleteUserRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserResourceApi();

  const body = {
    // string
    login: login_example,
  } satisfies DeleteUserRequest;

  try {
    const data = await api.deleteUser(body);
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
| **login** | `string` |  | [Defaults to `undefined`] |

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


## getAllUsers

> Array&lt;AdminUserDTO&gt; getAllUsers(page, size, sort)



### Example

```ts
import {
  Configuration,
  UserResourceApi,
} from '';
import type { GetAllUsersRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserResourceApi();

  const body = {
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllUsersRequest;

  try {
    const data = await api.getAllUsers(body);
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
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;AdminUserDTO&gt;**](AdminUserDTO.md)

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


## getUser

> AdminUserDTO getUser(login)



### Example

```ts
import {
  Configuration,
  UserResourceApi,
} from '';
import type { GetUserRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserResourceApi();

  const body = {
    // string
    login: login_example,
  } satisfies GetUserRequest;

  try {
    const data = await api.getUser(body);
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
| **login** | `string` |  | [Defaults to `undefined`] |

### Return type

[**AdminUserDTO**](AdminUserDTO.md)

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


## updateUser

> AdminUserDTO updateUser(adminUserDTO)



### Example

```ts
import {
  Configuration,
  UserResourceApi,
} from '';
import type { UpdateUserRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserResourceApi();

  const body = {
    // AdminUserDTO
    adminUserDTO: ...,
  } satisfies UpdateUserRequest;

  try {
    const data = await api.updateUser(body);
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
| **adminUserDTO** | [AdminUserDTO](AdminUserDTO.md) |  | |

### Return type

[**AdminUserDTO**](AdminUserDTO.md)

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


## updateUser1

> AdminUserDTO updateUser1(login, adminUserDTO)



### Example

```ts
import {
  Configuration,
  UserResourceApi,
} from '';
import type { UpdateUser1Request } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserResourceApi();

  const body = {
    // string
    login: login_example,
    // AdminUserDTO
    adminUserDTO: ...,
  } satisfies UpdateUser1Request;

  try {
    const data = await api.updateUser1(body);
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
| **login** | `string` |  | [Defaults to `undefined`] |
| **adminUserDTO** | [AdminUserDTO](AdminUserDTO.md) |  | |

### Return type

[**AdminUserDTO**](AdminUserDTO.md)

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

