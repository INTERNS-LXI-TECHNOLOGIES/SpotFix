# DepartmentResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countDepartments**](DepartmentResourceApi.md#countdepartments) | **GET** /api/departments/count |  |
| [**createDepartment**](DepartmentResourceApi.md#createdepartment) | **POST** /api/departments |  |
| [**deleteDepartment**](DepartmentResourceApi.md#deletedepartment) | **DELETE** /api/departments/{id} |  |
| [**getAllDepartments**](DepartmentResourceApi.md#getalldepartments) | **GET** /api/departments |  |
| [**getDepartment**](DepartmentResourceApi.md#getdepartment) | **GET** /api/departments/{id} |  |
| [**partialUpdateDepartment**](DepartmentResourceApi.md#partialupdatedepartment) | **PATCH** /api/departments/{id} |  |
| [**updateDepartment**](DepartmentResourceApi.md#updatedepartment) | **PUT** /api/departments/{id} |  |



## countDepartments

> number countDepartments(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, nameContains, nameDoesNotContain, nameEquals, nameNotEquals, nameSpecified, nameIn, nameNotIn, contactEmailContains, contactEmailDoesNotContain, contactEmailEquals, contactEmailNotEquals, contactEmailSpecified, contactEmailIn, contactEmailNotIn, contactPhoneContains, contactPhoneDoesNotContain, contactPhoneEquals, contactPhoneNotEquals, contactPhoneSpecified, contactPhoneIn, contactPhoneNotIn, activeEquals, activeNotEquals, activeSpecified, activeIn, activeNotIn, distinct)



### Example

```ts
import {
  Configuration,
  DepartmentResourceApi,
} from '';
import type { CountDepartmentsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new DepartmentResourceApi();

  const body = {
    // number (optional)
    idGreaterThan: 789,
    // number (optional)
    idLessThan: 789,
    // number (optional)
    idGreaterThanOrEqual: 789,
    // number (optional)
    idLessThanOrEqual: 789,
    // number (optional)
    idEquals: 789,
    // number (optional)
    idNotEquals: 789,
    // boolean (optional)
    idSpecified: true,
    // Array<number> (optional)
    idIn: ...,
    // Array<number> (optional)
    idNotIn: ...,
    // string (optional)
    nameContains: nameContains_example,
    // string (optional)
    nameDoesNotContain: nameDoesNotContain_example,
    // string (optional)
    nameEquals: nameEquals_example,
    // string (optional)
    nameNotEquals: nameNotEquals_example,
    // boolean (optional)
    nameSpecified: true,
    // Array<string> (optional)
    nameIn: ...,
    // Array<string> (optional)
    nameNotIn: ...,
    // string (optional)
    contactEmailContains: contactEmailContains_example,
    // string (optional)
    contactEmailDoesNotContain: contactEmailDoesNotContain_example,
    // string (optional)
    contactEmailEquals: contactEmailEquals_example,
    // string (optional)
    contactEmailNotEquals: contactEmailNotEquals_example,
    // boolean (optional)
    contactEmailSpecified: true,
    // Array<string> (optional)
    contactEmailIn: ...,
    // Array<string> (optional)
    contactEmailNotIn: ...,
    // string (optional)
    contactPhoneContains: contactPhoneContains_example,
    // string (optional)
    contactPhoneDoesNotContain: contactPhoneDoesNotContain_example,
    // string (optional)
    contactPhoneEquals: contactPhoneEquals_example,
    // string (optional)
    contactPhoneNotEquals: contactPhoneNotEquals_example,
    // boolean (optional)
    contactPhoneSpecified: true,
    // Array<string> (optional)
    contactPhoneIn: ...,
    // Array<string> (optional)
    contactPhoneNotIn: ...,
    // boolean (optional)
    activeEquals: true,
    // boolean (optional)
    activeNotEquals: true,
    // boolean (optional)
    activeSpecified: true,
    // Array<boolean> (optional)
    activeIn: ...,
    // Array<boolean> (optional)
    activeNotIn: ...,
    // boolean (optional)
    distinct: true,
  } satisfies CountDepartmentsRequest;

  try {
    const data = await api.countDepartments(body);
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
| **idGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **idIn** | `Array<number>` |  | [Optional] |
| **idNotIn** | `Array<number>` |  | [Optional] |
| **nameContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **nameIn** | `Array<string>` |  | [Optional] |
| **nameNotIn** | `Array<string>` |  | [Optional] |
| **contactEmailContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailIn** | `Array<string>` |  | [Optional] |
| **contactEmailNotIn** | `Array<string>` |  | [Optional] |
| **contactPhoneContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneIn** | `Array<string>` |  | [Optional] |
| **contactPhoneNotIn** | `Array<string>` |  | [Optional] |
| **activeEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **activeNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **activeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **activeIn** | `Array<boolean>` |  | [Optional] |
| **activeNotIn** | `Array<boolean>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |

### Return type

**number**

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


## createDepartment

> DepartmentDTO createDepartment(departmentDTO)



### Example

```ts
import {
  Configuration,
  DepartmentResourceApi,
} from '';
import type { CreateDepartmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new DepartmentResourceApi();

  const body = {
    // DepartmentDTO
    departmentDTO: ...,
  } satisfies CreateDepartmentRequest;

  try {
    const data = await api.createDepartment(body);
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
| **departmentDTO** | [DepartmentDTO](DepartmentDTO.md) |  | |

### Return type

[**DepartmentDTO**](DepartmentDTO.md)

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


## deleteDepartment

> deleteDepartment(id)



### Example

```ts
import {
  Configuration,
  DepartmentResourceApi,
} from '';
import type { DeleteDepartmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new DepartmentResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteDepartmentRequest;

  try {
    const data = await api.deleteDepartment(body);
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


## getAllDepartments

> Array&lt;DepartmentDTO&gt; getAllDepartments(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, nameContains, nameDoesNotContain, nameEquals, nameNotEquals, nameSpecified, nameIn, nameNotIn, contactEmailContains, contactEmailDoesNotContain, contactEmailEquals, contactEmailNotEquals, contactEmailSpecified, contactEmailIn, contactEmailNotIn, contactPhoneContains, contactPhoneDoesNotContain, contactPhoneEquals, contactPhoneNotEquals, contactPhoneSpecified, contactPhoneIn, contactPhoneNotIn, activeEquals, activeNotEquals, activeSpecified, activeIn, activeNotIn, distinct, page, size, sort)



### Example

```ts
import {
  Configuration,
  DepartmentResourceApi,
} from '';
import type { GetAllDepartmentsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new DepartmentResourceApi();

  const body = {
    // number (optional)
    idGreaterThan: 789,
    // number (optional)
    idLessThan: 789,
    // number (optional)
    idGreaterThanOrEqual: 789,
    // number (optional)
    idLessThanOrEqual: 789,
    // number (optional)
    idEquals: 789,
    // number (optional)
    idNotEquals: 789,
    // boolean (optional)
    idSpecified: true,
    // Array<number> (optional)
    idIn: ...,
    // Array<number> (optional)
    idNotIn: ...,
    // string (optional)
    nameContains: nameContains_example,
    // string (optional)
    nameDoesNotContain: nameDoesNotContain_example,
    // string (optional)
    nameEquals: nameEquals_example,
    // string (optional)
    nameNotEquals: nameNotEquals_example,
    // boolean (optional)
    nameSpecified: true,
    // Array<string> (optional)
    nameIn: ...,
    // Array<string> (optional)
    nameNotIn: ...,
    // string (optional)
    contactEmailContains: contactEmailContains_example,
    // string (optional)
    contactEmailDoesNotContain: contactEmailDoesNotContain_example,
    // string (optional)
    contactEmailEquals: contactEmailEquals_example,
    // string (optional)
    contactEmailNotEquals: contactEmailNotEquals_example,
    // boolean (optional)
    contactEmailSpecified: true,
    // Array<string> (optional)
    contactEmailIn: ...,
    // Array<string> (optional)
    contactEmailNotIn: ...,
    // string (optional)
    contactPhoneContains: contactPhoneContains_example,
    // string (optional)
    contactPhoneDoesNotContain: contactPhoneDoesNotContain_example,
    // string (optional)
    contactPhoneEquals: contactPhoneEquals_example,
    // string (optional)
    contactPhoneNotEquals: contactPhoneNotEquals_example,
    // boolean (optional)
    contactPhoneSpecified: true,
    // Array<string> (optional)
    contactPhoneIn: ...,
    // Array<string> (optional)
    contactPhoneNotIn: ...,
    // boolean (optional)
    activeEquals: true,
    // boolean (optional)
    activeNotEquals: true,
    // boolean (optional)
    activeSpecified: true,
    // Array<boolean> (optional)
    activeIn: ...,
    // Array<boolean> (optional)
    activeNotIn: ...,
    // boolean (optional)
    distinct: true,
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllDepartmentsRequest;

  try {
    const data = await api.getAllDepartments(body);
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
| **idGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **idSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **idIn** | `Array<number>` |  | [Optional] |
| **idNotIn** | `Array<number>` |  | [Optional] |
| **nameContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **nameIn** | `Array<string>` |  | [Optional] |
| **nameNotIn** | `Array<string>` |  | [Optional] |
| **contactEmailContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **contactEmailIn** | `Array<string>` |  | [Optional] |
| **contactEmailNotIn** | `Array<string>` |  | [Optional] |
| **contactPhoneContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **contactPhoneIn** | `Array<string>` |  | [Optional] |
| **contactPhoneNotIn** | `Array<string>` |  | [Optional] |
| **activeEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **activeNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **activeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **activeIn** | `Array<boolean>` |  | [Optional] |
| **activeNotIn** | `Array<boolean>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;DepartmentDTO&gt;**](DepartmentDTO.md)

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


## getDepartment

> DepartmentDTO getDepartment(id)



### Example

```ts
import {
  Configuration,
  DepartmentResourceApi,
} from '';
import type { GetDepartmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new DepartmentResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetDepartmentRequest;

  try {
    const data = await api.getDepartment(body);
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

[**DepartmentDTO**](DepartmentDTO.md)

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


## partialUpdateDepartment

> DepartmentDTO partialUpdateDepartment(id, departmentDTO)



### Example

```ts
import {
  Configuration,
  DepartmentResourceApi,
} from '';
import type { PartialUpdateDepartmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new DepartmentResourceApi();

  const body = {
    // number
    id: 789,
    // DepartmentDTO
    departmentDTO: ...,
  } satisfies PartialUpdateDepartmentRequest;

  try {
    const data = await api.partialUpdateDepartment(body);
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
| **departmentDTO** | [DepartmentDTO](DepartmentDTO.md) |  | |

### Return type

[**DepartmentDTO**](DepartmentDTO.md)

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


## updateDepartment

> DepartmentDTO updateDepartment(id, departmentDTO)



### Example

```ts
import {
  Configuration,
  DepartmentResourceApi,
} from '';
import type { UpdateDepartmentRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new DepartmentResourceApi();

  const body = {
    // number
    id: 789,
    // DepartmentDTO
    departmentDTO: ...,
  } satisfies UpdateDepartmentRequest;

  try {
    const data = await api.updateDepartment(body);
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
| **departmentDTO** | [DepartmentDTO](DepartmentDTO.md) |  | |

### Return type

[**DepartmentDTO**](DepartmentDTO.md)

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

