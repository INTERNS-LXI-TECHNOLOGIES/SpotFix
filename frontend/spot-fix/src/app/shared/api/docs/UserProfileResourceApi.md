# UserProfileResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createUserProfile**](UserProfileResourceApi.md#createuserprofile) | **POST** /api/user-profiles |  |
| [**deleteUserProfile**](UserProfileResourceApi.md#deleteuserprofile) | **DELETE** /api/user-profiles/{id} |  |
| [**getAllUserProfiles**](UserProfileResourceApi.md#getalluserprofiles) | **GET** /api/user-profiles |  |
| [**getUserProfile**](UserProfileResourceApi.md#getuserprofile) | **GET** /api/user-profiles/{id} |  |
| [**partialUpdateUserProfile**](UserProfileResourceApi.md#partialupdateuserprofile) | **PATCH** /api/user-profiles/{id} |  |
| [**updateUserProfile**](UserProfileResourceApi.md#updateuserprofile) | **PUT** /api/user-profiles/{id} |  |



## createUserProfile

> UserProfileDTO createUserProfile(userProfileDTO)



### Example

```ts
import {
  Configuration,
  UserProfileResourceApi,
} from '';
import type { CreateUserProfileRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserProfileResourceApi();

  const body = {
    // UserProfileDTO
    userProfileDTO: ...,
  } satisfies CreateUserProfileRequest;

  try {
    const data = await api.createUserProfile(body);
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
| **userProfileDTO** | [UserProfileDTO](UserProfileDTO.md) |  | |

### Return type

[**UserProfileDTO**](UserProfileDTO.md)

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


## deleteUserProfile

> deleteUserProfile(id)



### Example

```ts
import {
  Configuration,
  UserProfileResourceApi,
} from '';
import type { DeleteUserProfileRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserProfileResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteUserProfileRequest;

  try {
    const data = await api.deleteUserProfile(body);
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
| **id** | `number` |  | [Defaults to `undefined`] |

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


## getAllUserProfiles

> Array&lt;UserProfileDTO&gt; getAllUserProfiles(page, size, sort, eagerload)



### Example

```ts
import {
  Configuration,
  UserProfileResourceApi,
} from '';
import type { GetAllUserProfilesRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserProfileResourceApi();

  const body = {
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
    // boolean (optional)
    eagerload: true,
  } satisfies GetAllUserProfilesRequest;

  try {
    const data = await api.getAllUserProfiles(body);
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
| **eagerload** | `boolean` |  | [Optional] [Defaults to `true`] |

### Return type

[**Array&lt;UserProfileDTO&gt;**](UserProfileDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `application/json`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getUserProfile

> UserProfileDTO getUserProfile(id)



### Example

```ts
import {
  Configuration,
  UserProfileResourceApi,
} from '';
import type { GetUserProfileRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserProfileResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetUserProfileRequest;

  try {
    const data = await api.getUserProfile(body);
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
| **id** | `number` |  | [Defaults to `undefined`] |

### Return type

[**UserProfileDTO**](UserProfileDTO.md)

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


## partialUpdateUserProfile

> UserProfileDTO partialUpdateUserProfile(id, userProfileDTO)



### Example

```ts
import {
  Configuration,
  UserProfileResourceApi,
} from '';
import type { PartialUpdateUserProfileRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserProfileResourceApi();

  const body = {
    // number
    id: 789,
    // UserProfileDTO
    userProfileDTO: ...,
  } satisfies PartialUpdateUserProfileRequest;

  try {
    const data = await api.partialUpdateUserProfile(body);
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
| **id** | `number` |  | [Defaults to `undefined`] |
| **userProfileDTO** | [UserProfileDTO](UserProfileDTO.md) |  | |

### Return type

[**UserProfileDTO**](UserProfileDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`, `application/merge-patch+json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## updateUserProfile

> UserProfileDTO updateUserProfile(id, userProfileDTO)



### Example

```ts
import {
  Configuration,
  UserProfileResourceApi,
} from '';
import type { UpdateUserProfileRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new UserProfileResourceApi();

  const body = {
    // number
    id: 789,
    // UserProfileDTO
    userProfileDTO: ...,
  } satisfies UpdateUserProfileRequest;

  try {
    const data = await api.updateUserProfile(body);
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
| **id** | `number` |  | [Defaults to `undefined`] |
| **userProfileDTO** | [UserProfileDTO](UserProfileDTO.md) |  | |

### Return type

[**UserProfileDTO**](UserProfileDTO.md)

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

