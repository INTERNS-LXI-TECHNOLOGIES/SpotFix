# TicketVoteResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countTicketVotes**](TicketVoteResourceApi.md#countticketvotes) | **GET** /api/ticket-votes/count |  |
| [**createTicketVote**](TicketVoteResourceApi.md#createticketvote) | **POST** /api/ticket-votes |  |
| [**deleteTicketVote**](TicketVoteResourceApi.md#deleteticketvote) | **DELETE** /api/ticket-votes/{id} |  |
| [**getAllTicketVotes**](TicketVoteResourceApi.md#getallticketvotes) | **GET** /api/ticket-votes |  |
| [**getTicketVote**](TicketVoteResourceApi.md#getticketvote) | **GET** /api/ticket-votes/{id} |  |
| [**partialUpdateTicketVote**](TicketVoteResourceApi.md#partialupdateticketvote) | **PATCH** /api/ticket-votes/{id} |  |
| [**updateTicketVote**](TicketVoteResourceApi.md#updateticketvote) | **PUT** /api/ticket-votes/{id} |  |



## countTicketVotes

> number countTicketVotes(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, voteTypeEquals, voteTypeNotEquals, voteTypeSpecified, voteTypeIn, voteTypeNotIn, createdDateGreaterThan, createdDateLessThan, createdDateGreaterThanOrEqual, createdDateLessThanOrEqual, createdDateEquals, createdDateNotEquals, createdDateSpecified, createdDateIn, createdDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, userIdGreaterThan, userIdLessThan, userIdGreaterThanOrEqual, userIdLessThanOrEqual, userIdEquals, userIdNotEquals, userIdSpecified, userIdIn, userIdNotIn, distinct)



### Example

```ts
import {
  Configuration,
  TicketVoteResourceApi,
} from '';
import type { CountTicketVotesRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketVoteResourceApi();

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
    // 'UPVOTE' | 'DOWNVOTE' (optional)
    voteTypeEquals: voteTypeEquals_example,
    // 'UPVOTE' | 'DOWNVOTE' (optional)
    voteTypeNotEquals: voteTypeNotEquals_example,
    // boolean (optional)
    voteTypeSpecified: true,
    // Array<'UPVOTE' | 'DOWNVOTE'> (optional)
    voteTypeIn: ...,
    // Array<'UPVOTE' | 'DOWNVOTE'> (optional)
    voteTypeNotIn: ...,
    // Date (optional)
    createdDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    createdDateSpecified: true,
    // Array<Date> (optional)
    createdDateIn: ...,
    // Array<Date> (optional)
    createdDateNotIn: ...,
    // number (optional)
    ticketIdGreaterThan: 789,
    // number (optional)
    ticketIdLessThan: 789,
    // number (optional)
    ticketIdGreaterThanOrEqual: 789,
    // number (optional)
    ticketIdLessThanOrEqual: 789,
    // number (optional)
    ticketIdEquals: 789,
    // number (optional)
    ticketIdNotEquals: 789,
    // boolean (optional)
    ticketIdSpecified: true,
    // Array<number> (optional)
    ticketIdIn: ...,
    // Array<number> (optional)
    ticketIdNotIn: ...,
    // number (optional)
    userIdGreaterThan: 789,
    // number (optional)
    userIdLessThan: 789,
    // number (optional)
    userIdGreaterThanOrEqual: 789,
    // number (optional)
    userIdLessThanOrEqual: 789,
    // number (optional)
    userIdEquals: 789,
    // number (optional)
    userIdNotEquals: 789,
    // boolean (optional)
    userIdSpecified: true,
    // Array<number> (optional)
    userIdIn: ...,
    // Array<number> (optional)
    userIdNotIn: ...,
    // boolean (optional)
    distinct: true,
  } satisfies CountTicketVotesRequest;

  try {
    const data = await api.countTicketVotes(body);
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
| **voteTypeEquals** | `UPVOTE`, `DOWNVOTE` |  | [Optional] [Defaults to `undefined`] [Enum: UPVOTE, DOWNVOTE] |
| **voteTypeNotEquals** | `UPVOTE`, `DOWNVOTE` |  | [Optional] [Defaults to `undefined`] [Enum: UPVOTE, DOWNVOTE] |
| **voteTypeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **voteTypeIn** | `UPVOTE`, `DOWNVOTE` |  | [Optional] [Enum: UPVOTE, DOWNVOTE] |
| **voteTypeNotIn** | `UPVOTE`, `DOWNVOTE` |  | [Optional] [Enum: UPVOTE, DOWNVOTE] |
| **createdDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **createdDateIn** | `Array<Date>` |  | [Optional] |
| **createdDateNotIn** | `Array<Date>` |  | [Optional] |
| **ticketIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdIn** | `Array<number>` |  | [Optional] |
| **ticketIdNotIn** | `Array<number>` |  | [Optional] |
| **userIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **userIdIn** | `Array<number>` |  | [Optional] |
| **userIdNotIn** | `Array<number>` |  | [Optional] |
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


## createTicketVote

> TicketVoteDTO createTicketVote(ticketVoteDTO)



### Example

```ts
import {
  Configuration,
  TicketVoteResourceApi,
} from '';
import type { CreateTicketVoteRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketVoteResourceApi();

  const body = {
    // TicketVoteDTO
    ticketVoteDTO: ...,
  } satisfies CreateTicketVoteRequest;

  try {
    const data = await api.createTicketVote(body);
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
| **ticketVoteDTO** | [TicketVoteDTO](TicketVoteDTO.md) |  | |

### Return type

[**TicketVoteDTO**](TicketVoteDTO.md)

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


## deleteTicketVote

> deleteTicketVote(id)



### Example

```ts
import {
  Configuration,
  TicketVoteResourceApi,
} from '';
import type { DeleteTicketVoteRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketVoteResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteTicketVoteRequest;

  try {
    const data = await api.deleteTicketVote(body);
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


## getAllTicketVotes

> Array&lt;TicketVoteDTO&gt; getAllTicketVotes(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, voteTypeEquals, voteTypeNotEquals, voteTypeSpecified, voteTypeIn, voteTypeNotIn, createdDateGreaterThan, createdDateLessThan, createdDateGreaterThanOrEqual, createdDateLessThanOrEqual, createdDateEquals, createdDateNotEquals, createdDateSpecified, createdDateIn, createdDateNotIn, ticketIdGreaterThan, ticketIdLessThan, ticketIdGreaterThanOrEqual, ticketIdLessThanOrEqual, ticketIdEquals, ticketIdNotEquals, ticketIdSpecified, ticketIdIn, ticketIdNotIn, userIdGreaterThan, userIdLessThan, userIdGreaterThanOrEqual, userIdLessThanOrEqual, userIdEquals, userIdNotEquals, userIdSpecified, userIdIn, userIdNotIn, distinct, page, size, sort)



### Example

```ts
import {
  Configuration,
  TicketVoteResourceApi,
} from '';
import type { GetAllTicketVotesRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketVoteResourceApi();

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
    // 'UPVOTE' | 'DOWNVOTE' (optional)
    voteTypeEquals: voteTypeEquals_example,
    // 'UPVOTE' | 'DOWNVOTE' (optional)
    voteTypeNotEquals: voteTypeNotEquals_example,
    // boolean (optional)
    voteTypeSpecified: true,
    // Array<'UPVOTE' | 'DOWNVOTE'> (optional)
    voteTypeIn: ...,
    // Array<'UPVOTE' | 'DOWNVOTE'> (optional)
    voteTypeNotIn: ...,
    // Date (optional)
    createdDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    createdDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    createdDateSpecified: true,
    // Array<Date> (optional)
    createdDateIn: ...,
    // Array<Date> (optional)
    createdDateNotIn: ...,
    // number (optional)
    ticketIdGreaterThan: 789,
    // number (optional)
    ticketIdLessThan: 789,
    // number (optional)
    ticketIdGreaterThanOrEqual: 789,
    // number (optional)
    ticketIdLessThanOrEqual: 789,
    // number (optional)
    ticketIdEquals: 789,
    // number (optional)
    ticketIdNotEquals: 789,
    // boolean (optional)
    ticketIdSpecified: true,
    // Array<number> (optional)
    ticketIdIn: ...,
    // Array<number> (optional)
    ticketIdNotIn: ...,
    // number (optional)
    userIdGreaterThan: 789,
    // number (optional)
    userIdLessThan: 789,
    // number (optional)
    userIdGreaterThanOrEqual: 789,
    // number (optional)
    userIdLessThanOrEqual: 789,
    // number (optional)
    userIdEquals: 789,
    // number (optional)
    userIdNotEquals: 789,
    // boolean (optional)
    userIdSpecified: true,
    // Array<number> (optional)
    userIdIn: ...,
    // Array<number> (optional)
    userIdNotIn: ...,
    // boolean (optional)
    distinct: true,
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllTicketVotesRequest;

  try {
    const data = await api.getAllTicketVotes(body);
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
| **voteTypeEquals** | `UPVOTE`, `DOWNVOTE` |  | [Optional] [Defaults to `undefined`] [Enum: UPVOTE, DOWNVOTE] |
| **voteTypeNotEquals** | `UPVOTE`, `DOWNVOTE` |  | [Optional] [Defaults to `undefined`] [Enum: UPVOTE, DOWNVOTE] |
| **voteTypeSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **voteTypeIn** | `UPVOTE`, `DOWNVOTE` |  | [Optional] [Enum: UPVOTE, DOWNVOTE] |
| **voteTypeNotIn** | `UPVOTE`, `DOWNVOTE` |  | [Optional] [Enum: UPVOTE, DOWNVOTE] |
| **createdDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **createdDateIn** | `Array<Date>` |  | [Optional] |
| **createdDateNotIn** | `Array<Date>` |  | [Optional] |
| **ticketIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **ticketIdIn** | `Array<number>` |  | [Optional] |
| **ticketIdNotIn** | `Array<number>` |  | [Optional] |
| **userIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **userIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **userIdIn** | `Array<number>` |  | [Optional] |
| **userIdNotIn** | `Array<number>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;TicketVoteDTO&gt;**](TicketVoteDTO.md)

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


## getTicketVote

> TicketVoteDTO getTicketVote(id)



### Example

```ts
import {
  Configuration,
  TicketVoteResourceApi,
} from '';
import type { GetTicketVoteRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketVoteResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetTicketVoteRequest;

  try {
    const data = await api.getTicketVote(body);
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

[**TicketVoteDTO**](TicketVoteDTO.md)

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


## partialUpdateTicketVote

> TicketVoteDTO partialUpdateTicketVote(id, ticketVoteDTO)



### Example

```ts
import {
  Configuration,
  TicketVoteResourceApi,
} from '';
import type { PartialUpdateTicketVoteRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketVoteResourceApi();

  const body = {
    // number
    id: 789,
    // TicketVoteDTO
    ticketVoteDTO: ...,
  } satisfies PartialUpdateTicketVoteRequest;

  try {
    const data = await api.partialUpdateTicketVote(body);
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
| **ticketVoteDTO** | [TicketVoteDTO](TicketVoteDTO.md) |  | |

### Return type

[**TicketVoteDTO**](TicketVoteDTO.md)

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


## updateTicketVote

> TicketVoteDTO updateTicketVote(id, ticketVoteDTO)



### Example

```ts
import {
  Configuration,
  TicketVoteResourceApi,
} from '';
import type { UpdateTicketVoteRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketVoteResourceApi();

  const body = {
    // number
    id: 789,
    // TicketVoteDTO
    ticketVoteDTO: ...,
  } satisfies UpdateTicketVoteRequest;

  try {
    const data = await api.updateTicketVote(body);
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
| **ticketVoteDTO** | [TicketVoteDTO](TicketVoteDTO.md) |  | |

### Return type

[**TicketVoteDTO**](TicketVoteDTO.md)

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

