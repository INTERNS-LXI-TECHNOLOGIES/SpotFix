# WardResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countWards**](WardResourceApi.md#countwards) | **GET** /api/wards/count |  |
| [**createWard**](WardResourceApi.md#createward) | **POST** /api/wards |  |
| [**deleteWard**](WardResourceApi.md#deleteward) | **DELETE** /api/wards/{id} |  |
| [**getAllWards**](WardResourceApi.md#getallwards) | **GET** /api/wards |  |
| [**getWard**](WardResourceApi.md#getward) | **GET** /api/wards/{id} |  |
| [**partialUpdateWard**](WardResourceApi.md#partialupdateward) | **PATCH** /api/wards/{id} |  |
| [**updateWard**](WardResourceApi.md#updateward) | **PUT** /api/wards/{id} |  |



## countWards

> number countWards(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, codeContains, codeDoesNotContain, codeEquals, codeNotEquals, codeSpecified, codeIn, codeNotIn, nameContains, nameDoesNotContain, nameEquals, nameNotEquals, nameSpecified, nameIn, nameNotIn, municipalityContains, municipalityDoesNotContain, municipalityEquals, municipalityNotEquals, municipalitySpecified, municipalityIn, municipalityNotIn, distinct)



### Example

```ts
import {
  Configuration,
  WardResourceApi,
} from '';
import type { CountWardsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WardResourceApi();

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
    codeContains: codeContains_example,
    // string (optional)
    codeDoesNotContain: codeDoesNotContain_example,
    // string (optional)
    codeEquals: codeEquals_example,
    // string (optional)
    codeNotEquals: codeNotEquals_example,
    // boolean (optional)
    codeSpecified: true,
    // Array<string> (optional)
    codeIn: ...,
    // Array<string> (optional)
    codeNotIn: ...,
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
    municipalityContains: municipalityContains_example,
    // string (optional)
    municipalityDoesNotContain: municipalityDoesNotContain_example,
    // string (optional)
    municipalityEquals: municipalityEquals_example,
    // string (optional)
    municipalityNotEquals: municipalityNotEquals_example,
    // boolean (optional)
    municipalitySpecified: true,
    // Array<string> (optional)
    municipalityIn: ...,
    // Array<string> (optional)
    municipalityNotIn: ...,
    // boolean (optional)
    distinct: true,
  } satisfies CountWardsRequest;

  try {
    const data = await api.countWards(body);
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
| **codeContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **codeDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **codeEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **codeNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **codeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **codeIn** | `Array<string>` |  | [Optional] |
| **codeNotIn** | `Array<string>` |  | [Optional] |
| **nameContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **nameIn** | `Array<string>` |  | [Optional] |
| **nameNotIn** | `Array<string>` |  | [Optional] |
| **municipalityContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **municipalityDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **municipalityEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **municipalityNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **municipalitySpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **municipalityIn** | `Array<string>` |  | [Optional] |
| **municipalityNotIn** | `Array<string>` |  | [Optional] |
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


## createWard

> WardDTO createWard(wardDTO)



### Example

```ts
import {
  Configuration,
  WardResourceApi,
} from '';
import type { CreateWardRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WardResourceApi();

  const body = {
    // WardDTO
    wardDTO: ...,
  } satisfies CreateWardRequest;

  try {
    const data = await api.createWard(body);
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
| **wardDTO** | [WardDTO](WardDTO.md) |  | |

### Return type

[**WardDTO**](WardDTO.md)

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


## deleteWard

> deleteWard(id)



### Example

```ts
import {
  Configuration,
  WardResourceApi,
} from '';
import type { DeleteWardRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WardResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteWardRequest;

  try {
    const data = await api.deleteWard(body);
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


## getAllWards

> Array&lt;WardDTO&gt; getAllWards(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, codeContains, codeDoesNotContain, codeEquals, codeNotEquals, codeSpecified, codeIn, codeNotIn, nameContains, nameDoesNotContain, nameEquals, nameNotEquals, nameSpecified, nameIn, nameNotIn, municipalityContains, municipalityDoesNotContain, municipalityEquals, municipalityNotEquals, municipalitySpecified, municipalityIn, municipalityNotIn, distinct, page, size, sort)



### Example

```ts
import {
  Configuration,
  WardResourceApi,
} from '';
import type { GetAllWardsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WardResourceApi();

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
    codeContains: codeContains_example,
    // string (optional)
    codeDoesNotContain: codeDoesNotContain_example,
    // string (optional)
    codeEquals: codeEquals_example,
    // string (optional)
    codeNotEquals: codeNotEquals_example,
    // boolean (optional)
    codeSpecified: true,
    // Array<string> (optional)
    codeIn: ...,
    // Array<string> (optional)
    codeNotIn: ...,
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
    municipalityContains: municipalityContains_example,
    // string (optional)
    municipalityDoesNotContain: municipalityDoesNotContain_example,
    // string (optional)
    municipalityEquals: municipalityEquals_example,
    // string (optional)
    municipalityNotEquals: municipalityNotEquals_example,
    // boolean (optional)
    municipalitySpecified: true,
    // Array<string> (optional)
    municipalityIn: ...,
    // Array<string> (optional)
    municipalityNotIn: ...,
    // boolean (optional)
    distinct: true,
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllWardsRequest;

  try {
    const data = await api.getAllWards(body);
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
| **codeContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **codeDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **codeEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **codeNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **codeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **codeIn** | `Array<string>` |  | [Optional] |
| **codeNotIn** | `Array<string>` |  | [Optional] |
| **nameContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **nameSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **nameIn** | `Array<string>` |  | [Optional] |
| **nameNotIn** | `Array<string>` |  | [Optional] |
| **municipalityContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **municipalityDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **municipalityEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **municipalityNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **municipalitySpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **municipalityIn** | `Array<string>` |  | [Optional] |
| **municipalityNotIn** | `Array<string>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;WardDTO&gt;**](WardDTO.md)

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


## getWard

> WardDTO getWard(id)



### Example

```ts
import {
  Configuration,
  WardResourceApi,
} from '';
import type { GetWardRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WardResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetWardRequest;

  try {
    const data = await api.getWard(body);
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

[**WardDTO**](WardDTO.md)

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


## partialUpdateWard

> WardDTO partialUpdateWard(id, wardDTO)



### Example

```ts
import {
  Configuration,
  WardResourceApi,
} from '';
import type { PartialUpdateWardRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WardResourceApi();

  const body = {
    // number
    id: 789,
    // WardDTO
    wardDTO: ...,
  } satisfies PartialUpdateWardRequest;

  try {
    const data = await api.partialUpdateWard(body);
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
| **wardDTO** | [WardDTO](WardDTO.md) |  | |

### Return type

[**WardDTO**](WardDTO.md)

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


## updateWard

> WardDTO updateWard(id, wardDTO)



### Example

```ts
import {
  Configuration,
  WardResourceApi,
} from '';
import type { UpdateWardRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new WardResourceApi();

  const body = {
    // number
    id: 789,
    // WardDTO
    wardDTO: ...,
  } satisfies UpdateWardRequest;

  try {
    const data = await api.updateWard(body);
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
| **wardDTO** | [WardDTO](WardDTO.md) |  | |

### Return type

[**WardDTO**](WardDTO.md)

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

