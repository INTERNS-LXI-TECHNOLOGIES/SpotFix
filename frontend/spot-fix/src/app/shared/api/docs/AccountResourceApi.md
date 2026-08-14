# AccountResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**activateAccount**](AccountResourceApi.md#activateaccount) | **GET** /api/activate |  |
| [**changePassword**](AccountResourceApi.md#changepassword) | **POST** /api/account/change-password |  |
| [**finishPasswordReset**](AccountResourceApi.md#finishpasswordreset) | **POST** /api/account/reset-password/finish |  |
| [**getAccount**](AccountResourceApi.md#getaccount) | **GET** /api/account |  |
| [**registerAccount**](AccountResourceApi.md#registeraccount) | **POST** /api/register |  |
| [**requestPasswordReset**](AccountResourceApi.md#requestpasswordreset) | **POST** /api/account/reset-password/init |  |
| [**saveAccount**](AccountResourceApi.md#saveaccount) | **POST** /api/account |  |



## activateAccount

> activateAccount(key)



### Example

```ts
import {
  Configuration,
  AccountResourceApi,
} from '';
import type { ActivateAccountRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountResourceApi();

  const body = {
    // string
    key: key_example,
  } satisfies ActivateAccountRequest;

  try {
    const data = await api.activateAccount(body);
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
| **key** | `string` |  | [Defaults to `undefined`] |

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


## changePassword

> changePassword(passwordChangeDTO)



### Example

```ts
import {
  Configuration,
  AccountResourceApi,
} from '';
import type { ChangePasswordRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountResourceApi();

  const body = {
    // PasswordChangeDTO
    passwordChangeDTO: ...,
  } satisfies ChangePasswordRequest;

  try {
    const data = await api.changePassword(body);
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
| **passwordChangeDTO** | [PasswordChangeDTO](PasswordChangeDTO.md) |  | |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## finishPasswordReset

> finishPasswordReset(keyAndPasswordVM)



### Example

```ts
import {
  Configuration,
  AccountResourceApi,
} from '';
import type { FinishPasswordResetRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountResourceApi();

  const body = {
    // KeyAndPasswordVM
    keyAndPasswordVM: ...,
  } satisfies FinishPasswordResetRequest;

  try {
    const data = await api.finishPasswordReset(body);
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
| **keyAndPasswordVM** | [KeyAndPasswordVM](KeyAndPasswordVM.md) |  | |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getAccount

> AdminUserDTO getAccount()



### Example

```ts
import {
  Configuration,
  AccountResourceApi,
} from '';
import type { GetAccountRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountResourceApi();

  try {
    const data = await api.getAccount();
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


## registerAccount

> registerAccount(managedUserVM)



### Example

```ts
import {
  Configuration,
  AccountResourceApi,
} from '';
import type { RegisterAccountRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountResourceApi();

  const body = {
    // ManagedUserVM
    managedUserVM: ...,
  } satisfies RegisterAccountRequest;

  try {
    const data = await api.registerAccount(body);
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
| **managedUserVM** | [ManagedUserVM](ManagedUserVM.md) |  | |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Created |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## requestPasswordReset

> requestPasswordReset(body)



### Example

```ts
import {
  Configuration,
  AccountResourceApi,
} from '';
import type { RequestPasswordResetRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountResourceApi();

  const body = {
    // string
    body: body_example,
  } satisfies RequestPasswordResetRequest;

  try {
    const data = await api.requestPasswordReset(body);
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
| **body** | `string` |  | |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## saveAccount

> saveAccount(adminUserDTO)



### Example

```ts
import {
  Configuration,
  AccountResourceApi,
} from '';
import type { SaveAccountRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new AccountResourceApi();

  const body = {
    // AdminUserDTO
    adminUserDTO: ...,
  } satisfies SaveAccountRequest;

  try {
    const data = await api.saveAccount(body);
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

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)

