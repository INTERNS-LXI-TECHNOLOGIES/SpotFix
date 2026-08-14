# LocationResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countLocations**](LocationResourceApi.md#countlocations) | **GET** /api/locations/count |  |
| [**createLocation**](LocationResourceApi.md#createlocation) | **POST** /api/locations |  |
| [**deleteLocation**](LocationResourceApi.md#deletelocation) | **DELETE** /api/locations/{id} |  |
| [**getAllLocations**](LocationResourceApi.md#getalllocations) | **GET** /api/locations |  |
| [**getLocation**](LocationResourceApi.md#getlocation) | **GET** /api/locations/{id} |  |
| [**partialUpdateLocation**](LocationResourceApi.md#partialupdatelocation) | **PATCH** /api/locations/{id} |  |
| [**updateLocation**](LocationResourceApi.md#updatelocation) | **PUT** /api/locations/{id} |  |



## countLocations

> number countLocations(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, addressTextContains, addressTextDoesNotContain, addressTextEquals, addressTextNotEquals, addressTextSpecified, addressTextIn, addressTextNotIn, landmarkContains, landmarkDoesNotContain, landmarkEquals, landmarkNotEquals, landmarkSpecified, landmarkIn, landmarkNotIn, latitudeGreaterThan, latitudeLessThan, latitudeGreaterThanOrEqual, latitudeLessThanOrEqual, latitudeEquals, latitudeNotEquals, latitudeSpecified, latitudeIn, latitudeNotIn, longitudeGreaterThan, longitudeLessThan, longitudeGreaterThanOrEqual, longitudeLessThanOrEqual, longitudeEquals, longitudeNotEquals, longitudeSpecified, longitudeIn, longitudeNotIn, wardIdGreaterThan, wardIdLessThan, wardIdGreaterThanOrEqual, wardIdLessThanOrEqual, wardIdEquals, wardIdNotEquals, wardIdSpecified, wardIdIn, wardIdNotIn, distinct)



### Example

```ts
import {
  Configuration,
  LocationResourceApi,
} from '';
import type { CountLocationsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new LocationResourceApi();

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
    addressTextContains: addressTextContains_example,
    // string (optional)
    addressTextDoesNotContain: addressTextDoesNotContain_example,
    // string (optional)
    addressTextEquals: addressTextEquals_example,
    // string (optional)
    addressTextNotEquals: addressTextNotEquals_example,
    // boolean (optional)
    addressTextSpecified: true,
    // Array<string> (optional)
    addressTextIn: ...,
    // Array<string> (optional)
    addressTextNotIn: ...,
    // string (optional)
    landmarkContains: landmarkContains_example,
    // string (optional)
    landmarkDoesNotContain: landmarkDoesNotContain_example,
    // string (optional)
    landmarkEquals: landmarkEquals_example,
    // string (optional)
    landmarkNotEquals: landmarkNotEquals_example,
    // boolean (optional)
    landmarkSpecified: true,
    // Array<string> (optional)
    landmarkIn: ...,
    // Array<string> (optional)
    landmarkNotIn: ...,
    // number (optional)
    latitudeGreaterThan: 1.2,
    // number (optional)
    latitudeLessThan: 1.2,
    // number (optional)
    latitudeGreaterThanOrEqual: 1.2,
    // number (optional)
    latitudeLessThanOrEqual: 1.2,
    // number (optional)
    latitudeEquals: 1.2,
    // number (optional)
    latitudeNotEquals: 1.2,
    // boolean (optional)
    latitudeSpecified: true,
    // Array<number> (optional)
    latitudeIn: ...,
    // Array<number> (optional)
    latitudeNotIn: ...,
    // number (optional)
    longitudeGreaterThan: 1.2,
    // number (optional)
    longitudeLessThan: 1.2,
    // number (optional)
    longitudeGreaterThanOrEqual: 1.2,
    // number (optional)
    longitudeLessThanOrEqual: 1.2,
    // number (optional)
    longitudeEquals: 1.2,
    // number (optional)
    longitudeNotEquals: 1.2,
    // boolean (optional)
    longitudeSpecified: true,
    // Array<number> (optional)
    longitudeIn: ...,
    // Array<number> (optional)
    longitudeNotIn: ...,
    // number (optional)
    wardIdGreaterThan: 789,
    // number (optional)
    wardIdLessThan: 789,
    // number (optional)
    wardIdGreaterThanOrEqual: 789,
    // number (optional)
    wardIdLessThanOrEqual: 789,
    // number (optional)
    wardIdEquals: 789,
    // number (optional)
    wardIdNotEquals: 789,
    // boolean (optional)
    wardIdSpecified: true,
    // Array<number> (optional)
    wardIdIn: ...,
    // Array<number> (optional)
    wardIdNotIn: ...,
    // boolean (optional)
    distinct: true,
  } satisfies CountLocationsRequest;

  try {
    const data = await api.countLocations(body);
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
| **addressTextContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **addressTextDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **addressTextEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **addressTextNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **addressTextSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **addressTextIn** | `Array<string>` |  | [Optional] |
| **addressTextNotIn** | `Array<string>` |  | [Optional] |
| **landmarkContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **landmarkDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **landmarkEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **landmarkNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **landmarkSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **landmarkIn** | `Array<string>` |  | [Optional] |
| **landmarkNotIn** | `Array<string>` |  | [Optional] |
| **latitudeGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **latitudeIn** | `Array<number>` |  | [Optional] |
| **latitudeNotIn** | `Array<number>` |  | [Optional] |
| **longitudeGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **longitudeIn** | `Array<number>` |  | [Optional] |
| **longitudeNotIn** | `Array<number>` |  | [Optional] |
| **wardIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **wardIdIn** | `Array<number>` |  | [Optional] |
| **wardIdNotIn** | `Array<number>` |  | [Optional] |
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


## createLocation

> LocationDTO createLocation(locationDTO)



### Example

```ts
import {
  Configuration,
  LocationResourceApi,
} from '';
import type { CreateLocationRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new LocationResourceApi();

  const body = {
    // LocationDTO
    locationDTO: ...,
  } satisfies CreateLocationRequest;

  try {
    const data = await api.createLocation(body);
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
| **locationDTO** | [LocationDTO](LocationDTO.md) |  | |

### Return type

[**LocationDTO**](LocationDTO.md)

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


## deleteLocation

> deleteLocation(id)



### Example

```ts
import {
  Configuration,
  LocationResourceApi,
} from '';
import type { DeleteLocationRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new LocationResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteLocationRequest;

  try {
    const data = await api.deleteLocation(body);
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


## getAllLocations

> Array&lt;LocationDTO&gt; getAllLocations(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, addressTextContains, addressTextDoesNotContain, addressTextEquals, addressTextNotEquals, addressTextSpecified, addressTextIn, addressTextNotIn, landmarkContains, landmarkDoesNotContain, landmarkEquals, landmarkNotEquals, landmarkSpecified, landmarkIn, landmarkNotIn, latitudeGreaterThan, latitudeLessThan, latitudeGreaterThanOrEqual, latitudeLessThanOrEqual, latitudeEquals, latitudeNotEquals, latitudeSpecified, latitudeIn, latitudeNotIn, longitudeGreaterThan, longitudeLessThan, longitudeGreaterThanOrEqual, longitudeLessThanOrEqual, longitudeEquals, longitudeNotEquals, longitudeSpecified, longitudeIn, longitudeNotIn, wardIdGreaterThan, wardIdLessThan, wardIdGreaterThanOrEqual, wardIdLessThanOrEqual, wardIdEquals, wardIdNotEquals, wardIdSpecified, wardIdIn, wardIdNotIn, distinct, page, size, sort)



### Example

```ts
import {
  Configuration,
  LocationResourceApi,
} from '';
import type { GetAllLocationsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new LocationResourceApi();

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
    addressTextContains: addressTextContains_example,
    // string (optional)
    addressTextDoesNotContain: addressTextDoesNotContain_example,
    // string (optional)
    addressTextEquals: addressTextEquals_example,
    // string (optional)
    addressTextNotEquals: addressTextNotEquals_example,
    // boolean (optional)
    addressTextSpecified: true,
    // Array<string> (optional)
    addressTextIn: ...,
    // Array<string> (optional)
    addressTextNotIn: ...,
    // string (optional)
    landmarkContains: landmarkContains_example,
    // string (optional)
    landmarkDoesNotContain: landmarkDoesNotContain_example,
    // string (optional)
    landmarkEquals: landmarkEquals_example,
    // string (optional)
    landmarkNotEquals: landmarkNotEquals_example,
    // boolean (optional)
    landmarkSpecified: true,
    // Array<string> (optional)
    landmarkIn: ...,
    // Array<string> (optional)
    landmarkNotIn: ...,
    // number (optional)
    latitudeGreaterThan: 1.2,
    // number (optional)
    latitudeLessThan: 1.2,
    // number (optional)
    latitudeGreaterThanOrEqual: 1.2,
    // number (optional)
    latitudeLessThanOrEqual: 1.2,
    // number (optional)
    latitudeEquals: 1.2,
    // number (optional)
    latitudeNotEquals: 1.2,
    // boolean (optional)
    latitudeSpecified: true,
    // Array<number> (optional)
    latitudeIn: ...,
    // Array<number> (optional)
    latitudeNotIn: ...,
    // number (optional)
    longitudeGreaterThan: 1.2,
    // number (optional)
    longitudeLessThan: 1.2,
    // number (optional)
    longitudeGreaterThanOrEqual: 1.2,
    // number (optional)
    longitudeLessThanOrEqual: 1.2,
    // number (optional)
    longitudeEquals: 1.2,
    // number (optional)
    longitudeNotEquals: 1.2,
    // boolean (optional)
    longitudeSpecified: true,
    // Array<number> (optional)
    longitudeIn: ...,
    // Array<number> (optional)
    longitudeNotIn: ...,
    // number (optional)
    wardIdGreaterThan: 789,
    // number (optional)
    wardIdLessThan: 789,
    // number (optional)
    wardIdGreaterThanOrEqual: 789,
    // number (optional)
    wardIdLessThanOrEqual: 789,
    // number (optional)
    wardIdEquals: 789,
    // number (optional)
    wardIdNotEquals: 789,
    // boolean (optional)
    wardIdSpecified: true,
    // Array<number> (optional)
    wardIdIn: ...,
    // Array<number> (optional)
    wardIdNotIn: ...,
    // boolean (optional)
    distinct: true,
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllLocationsRequest;

  try {
    const data = await api.getAllLocations(body);
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
| **addressTextContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **addressTextDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **addressTextEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **addressTextNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **addressTextSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **addressTextIn** | `Array<string>` |  | [Optional] |
| **addressTextNotIn** | `Array<string>` |  | [Optional] |
| **landmarkContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **landmarkDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **landmarkEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **landmarkNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **landmarkSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **landmarkIn** | `Array<string>` |  | [Optional] |
| **landmarkNotIn** | `Array<string>` |  | [Optional] |
| **latitudeGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **latitudeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **latitudeIn** | `Array<number>` |  | [Optional] |
| **latitudeNotIn** | `Array<number>` |  | [Optional] |
| **longitudeGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **longitudeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **longitudeIn** | `Array<number>` |  | [Optional] |
| **longitudeNotIn** | `Array<number>` |  | [Optional] |
| **wardIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **wardIdIn** | `Array<number>` |  | [Optional] |
| **wardIdNotIn** | `Array<number>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;LocationDTO&gt;**](LocationDTO.md)

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


## getLocation

> LocationDTO getLocation(id)



### Example

```ts
import {
  Configuration,
  LocationResourceApi,
} from '';
import type { GetLocationRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new LocationResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetLocationRequest;

  try {
    const data = await api.getLocation(body);
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

[**LocationDTO**](LocationDTO.md)

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


## partialUpdateLocation

> LocationDTO partialUpdateLocation(id, locationDTO)



### Example

```ts
import {
  Configuration,
  LocationResourceApi,
} from '';
import type { PartialUpdateLocationRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new LocationResourceApi();

  const body = {
    // number
    id: 789,
    // LocationDTO
    locationDTO: ...,
  } satisfies PartialUpdateLocationRequest;

  try {
    const data = await api.partialUpdateLocation(body);
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
| **locationDTO** | [LocationDTO](LocationDTO.md) |  | |

### Return type

[**LocationDTO**](LocationDTO.md)

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


## updateLocation

> LocationDTO updateLocation(id, locationDTO)



### Example

```ts
import {
  Configuration,
  LocationResourceApi,
} from '';
import type { UpdateLocationRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new LocationResourceApi();

  const body = {
    // number
    id: 789,
    // LocationDTO
    locationDTO: ...,
  } satisfies UpdateLocationRequest;

  try {
    const data = await api.updateLocation(body);
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
| **locationDTO** | [LocationDTO](LocationDTO.md) |  | |

### Return type

[**LocationDTO**](LocationDTO.md)

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

