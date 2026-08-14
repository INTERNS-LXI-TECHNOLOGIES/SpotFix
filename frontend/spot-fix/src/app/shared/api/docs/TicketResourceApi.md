# TicketResourceApi

All URIs are relative to *http://localhost:8081*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**countTickets**](TicketResourceApi.md#counttickets) | **GET** /api/tickets/count |  |
| [**createTicket**](TicketResourceApi.md#createticket) | **POST** /api/tickets |  |
| [**deleteTicket**](TicketResourceApi.md#deleteticket) | **DELETE** /api/tickets/{id} |  |
| [**getAllTickets**](TicketResourceApi.md#getalltickets) | **GET** /api/tickets |  |
| [**getTicket**](TicketResourceApi.md#getticket) | **GET** /api/tickets/{id} |  |
| [**partialUpdateTicket**](TicketResourceApi.md#partialupdateticket) | **PATCH** /api/tickets/{id} |  |
| [**updateTicket**](TicketResourceApi.md#updateticket) | **PUT** /api/tickets/{id} |  |



## countTickets

> number countTickets(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, titleContains, titleDoesNotContain, titleEquals, titleNotEquals, titleSpecified, titleIn, titleNotIn, statusEquals, statusNotEquals, statusSpecified, statusIn, statusNotIn, priorityEquals, priorityNotEquals, prioritySpecified, priorityIn, priorityNotIn, visibilityEquals, visibilityNotEquals, visibilitySpecified, visibilityIn, visibilityNotIn, categoryEquals, categoryNotEquals, categorySpecified, categoryIn, categoryNotIn, createdDateGreaterThan, createdDateLessThan, createdDateGreaterThanOrEqual, createdDateLessThanOrEqual, createdDateEquals, createdDateNotEquals, createdDateSpecified, createdDateIn, createdDateNotIn, updatedDateGreaterThan, updatedDateLessThan, updatedDateGreaterThanOrEqual, updatedDateLessThanOrEqual, updatedDateEquals, updatedDateNotEquals, updatedDateSpecified, updatedDateIn, updatedDateNotIn, expectedResolutionDateGreaterThan, expectedResolutionDateLessThan, expectedResolutionDateGreaterThanOrEqual, expectedResolutionDateLessThanOrEqual, expectedResolutionDateEquals, expectedResolutionDateNotEquals, expectedResolutionDateSpecified, expectedResolutionDateIn, expectedResolutionDateNotIn, resolvedDateGreaterThan, resolvedDateLessThan, resolvedDateGreaterThanOrEqual, resolvedDateLessThanOrEqual, resolvedDateEquals, resolvedDateNotEquals, resolvedDateSpecified, resolvedDateIn, resolvedDateNotIn, aiDuplicateEquals, aiDuplicateNotEquals, aiDuplicateSpecified, aiDuplicateIn, aiDuplicateNotIn, duplicateScoreGreaterThan, duplicateScoreLessThan, duplicateScoreGreaterThanOrEqual, duplicateScoreLessThanOrEqual, duplicateScoreEquals, duplicateScoreNotEquals, duplicateScoreSpecified, duplicateScoreIn, duplicateScoreNotIn, aiConfidenceGreaterThan, aiConfidenceLessThan, aiConfidenceGreaterThanOrEqual, aiConfidenceLessThanOrEqual, aiConfidenceEquals, aiConfidenceNotEquals, aiConfidenceSpecified, aiConfidenceIn, aiConfidenceNotIn, duplicateTicketIdGreaterThan, duplicateTicketIdLessThan, duplicateTicketIdGreaterThanOrEqual, duplicateTicketIdLessThanOrEqual, duplicateTicketIdEquals, duplicateTicketIdNotEquals, duplicateTicketIdSpecified, duplicateTicketIdIn, duplicateTicketIdNotIn, deletedEquals, deletedNotEquals, deletedSpecified, deletedIn, deletedNotIn, deletedDateGreaterThan, deletedDateLessThan, deletedDateGreaterThanOrEqual, deletedDateLessThanOrEqual, deletedDateEquals, deletedDateNotEquals, deletedDateSpecified, deletedDateIn, deletedDateNotIn, reportedByIdGreaterThan, reportedByIdLessThan, reportedByIdGreaterThanOrEqual, reportedByIdLessThanOrEqual, reportedByIdEquals, reportedByIdNotEquals, reportedByIdSpecified, reportedByIdIn, reportedByIdNotIn, locationIdGreaterThan, locationIdLessThan, locationIdGreaterThanOrEqual, locationIdLessThanOrEqual, locationIdEquals, locationIdNotEquals, locationIdSpecified, locationIdIn, locationIdNotIn, wardIdGreaterThan, wardIdLessThan, wardIdGreaterThanOrEqual, wardIdLessThanOrEqual, wardIdEquals, wardIdNotEquals, wardIdSpecified, wardIdIn, wardIdNotIn, assignedDepartmentIdGreaterThan, assignedDepartmentIdLessThan, assignedDepartmentIdGreaterThanOrEqual, assignedDepartmentIdLessThanOrEqual, assignedDepartmentIdEquals, assignedDepartmentIdNotEquals, assignedDepartmentIdSpecified, assignedDepartmentIdIn, assignedDepartmentIdNotIn, distinct)



### Example

```ts
import {
  Configuration,
  TicketResourceApi,
} from '';
import type { CountTicketsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketResourceApi();

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
    titleContains: titleContains_example,
    // string (optional)
    titleDoesNotContain: titleDoesNotContain_example,
    // string (optional)
    titleEquals: titleEquals_example,
    // string (optional)
    titleNotEquals: titleNotEquals_example,
    // boolean (optional)
    titleSpecified: true,
    // Array<string> (optional)
    titleIn: ...,
    // Array<string> (optional)
    titleNotIn: ...,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    statusEquals: statusEquals_example,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    statusNotEquals: statusNotEquals_example,
    // boolean (optional)
    statusSpecified: true,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    statusIn: ...,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    statusNotIn: ...,
    // 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT' (optional)
    priorityEquals: priorityEquals_example,
    // 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT' (optional)
    priorityNotEquals: priorityNotEquals_example,
    // boolean (optional)
    prioritySpecified: true,
    // Array<'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT'> (optional)
    priorityIn: ...,
    // Array<'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT'> (optional)
    priorityNotIn: ...,
    // 'PUBLIC' | 'PRIVATE' (optional)
    visibilityEquals: visibilityEquals_example,
    // 'PUBLIC' | 'PRIVATE' (optional)
    visibilityNotEquals: visibilityNotEquals_example,
    // boolean (optional)
    visibilitySpecified: true,
    // Array<'PUBLIC' | 'PRIVATE'> (optional)
    visibilityIn: ...,
    // Array<'PUBLIC' | 'PRIVATE'> (optional)
    visibilityNotIn: ...,
    // 'ROAD_DAMAGE' | 'WATER_SUPPLY' | 'STREET_LIGHT' | 'DRAINAGE' | 'GARBAGE' | 'ELECTRICITY' | 'TREE' | 'OTHER' (optional)
    categoryEquals: categoryEquals_example,
    // 'ROAD_DAMAGE' | 'WATER_SUPPLY' | 'STREET_LIGHT' | 'DRAINAGE' | 'GARBAGE' | 'ELECTRICITY' | 'TREE' | 'OTHER' (optional)
    categoryNotEquals: categoryNotEquals_example,
    // boolean (optional)
    categorySpecified: true,
    // Array<'ROAD_DAMAGE' | 'WATER_SUPPLY' | 'STREET_LIGHT' | 'DRAINAGE' | 'GARBAGE' | 'ELECTRICITY' | 'TREE' | 'OTHER'> (optional)
    categoryIn: ...,
    // Array<'ROAD_DAMAGE' | 'WATER_SUPPLY' | 'STREET_LIGHT' | 'DRAINAGE' | 'GARBAGE' | 'ELECTRICITY' | 'TREE' | 'OTHER'> (optional)
    categoryNotIn: ...,
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
    // Date (optional)
    updatedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    updatedDateSpecified: true,
    // Array<Date> (optional)
    updatedDateIn: ...,
    // Array<Date> (optional)
    updatedDateNotIn: ...,
    // Date (optional)
    expectedResolutionDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    expectedResolutionDateSpecified: true,
    // Array<Date> (optional)
    expectedResolutionDateIn: ...,
    // Array<Date> (optional)
    expectedResolutionDateNotIn: ...,
    // Date (optional)
    resolvedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    resolvedDateSpecified: true,
    // Array<Date> (optional)
    resolvedDateIn: ...,
    // Array<Date> (optional)
    resolvedDateNotIn: ...,
    // boolean (optional)
    aiDuplicateEquals: true,
    // boolean (optional)
    aiDuplicateNotEquals: true,
    // boolean (optional)
    aiDuplicateSpecified: true,
    // Array<boolean> (optional)
    aiDuplicateIn: ...,
    // Array<boolean> (optional)
    aiDuplicateNotIn: ...,
    // number (optional)
    duplicateScoreGreaterThan: 1.2,
    // number (optional)
    duplicateScoreLessThan: 1.2,
    // number (optional)
    duplicateScoreGreaterThanOrEqual: 1.2,
    // number (optional)
    duplicateScoreLessThanOrEqual: 1.2,
    // number (optional)
    duplicateScoreEquals: 1.2,
    // number (optional)
    duplicateScoreNotEquals: 1.2,
    // boolean (optional)
    duplicateScoreSpecified: true,
    // Array<number> (optional)
    duplicateScoreIn: ...,
    // Array<number> (optional)
    duplicateScoreNotIn: ...,
    // number (optional)
    aiConfidenceGreaterThan: 1.2,
    // number (optional)
    aiConfidenceLessThan: 1.2,
    // number (optional)
    aiConfidenceGreaterThanOrEqual: 1.2,
    // number (optional)
    aiConfidenceLessThanOrEqual: 1.2,
    // number (optional)
    aiConfidenceEquals: 1.2,
    // number (optional)
    aiConfidenceNotEquals: 1.2,
    // boolean (optional)
    aiConfidenceSpecified: true,
    // Array<number> (optional)
    aiConfidenceIn: ...,
    // Array<number> (optional)
    aiConfidenceNotIn: ...,
    // number (optional)
    duplicateTicketIdGreaterThan: 789,
    // number (optional)
    duplicateTicketIdLessThan: 789,
    // number (optional)
    duplicateTicketIdGreaterThanOrEqual: 789,
    // number (optional)
    duplicateTicketIdLessThanOrEqual: 789,
    // number (optional)
    duplicateTicketIdEquals: 789,
    // number (optional)
    duplicateTicketIdNotEquals: 789,
    // boolean (optional)
    duplicateTicketIdSpecified: true,
    // Array<number> (optional)
    duplicateTicketIdIn: ...,
    // Array<number> (optional)
    duplicateTicketIdNotIn: ...,
    // boolean (optional)
    deletedEquals: true,
    // boolean (optional)
    deletedNotEquals: true,
    // boolean (optional)
    deletedSpecified: true,
    // Array<boolean> (optional)
    deletedIn: ...,
    // Array<boolean> (optional)
    deletedNotIn: ...,
    // Date (optional)
    deletedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    deletedDateSpecified: true,
    // Array<Date> (optional)
    deletedDateIn: ...,
    // Array<Date> (optional)
    deletedDateNotIn: ...,
    // number (optional)
    reportedByIdGreaterThan: 789,
    // number (optional)
    reportedByIdLessThan: 789,
    // number (optional)
    reportedByIdGreaterThanOrEqual: 789,
    // number (optional)
    reportedByIdLessThanOrEqual: 789,
    // number (optional)
    reportedByIdEquals: 789,
    // number (optional)
    reportedByIdNotEquals: 789,
    // boolean (optional)
    reportedByIdSpecified: true,
    // Array<number> (optional)
    reportedByIdIn: ...,
    // Array<number> (optional)
    reportedByIdNotIn: ...,
    // number (optional)
    locationIdGreaterThan: 789,
    // number (optional)
    locationIdLessThan: 789,
    // number (optional)
    locationIdGreaterThanOrEqual: 789,
    // number (optional)
    locationIdLessThanOrEqual: 789,
    // number (optional)
    locationIdEquals: 789,
    // number (optional)
    locationIdNotEquals: 789,
    // boolean (optional)
    locationIdSpecified: true,
    // Array<number> (optional)
    locationIdIn: ...,
    // Array<number> (optional)
    locationIdNotIn: ...,
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
    // number (optional)
    assignedDepartmentIdGreaterThan: 789,
    // number (optional)
    assignedDepartmentIdLessThan: 789,
    // number (optional)
    assignedDepartmentIdGreaterThanOrEqual: 789,
    // number (optional)
    assignedDepartmentIdLessThanOrEqual: 789,
    // number (optional)
    assignedDepartmentIdEquals: 789,
    // number (optional)
    assignedDepartmentIdNotEquals: 789,
    // boolean (optional)
    assignedDepartmentIdSpecified: true,
    // Array<number> (optional)
    assignedDepartmentIdIn: ...,
    // Array<number> (optional)
    assignedDepartmentIdNotIn: ...,
    // boolean (optional)
    distinct: true,
  } satisfies CountTicketsRequest;

  try {
    const data = await api.countTickets(body);
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
| **titleContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **titleIn** | `Array<string>` |  | [Optional] |
| **titleNotIn** | `Array<string>` |  | [Optional] |
| **statusEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **statusNotEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **statusSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **statusIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **statusNotIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **priorityEquals** | `LOW`, `MEDIUM`, `HIGH`, `URGENT` |  | [Optional] [Defaults to `undefined`] [Enum: LOW, MEDIUM, HIGH, URGENT] |
| **priorityNotEquals** | `LOW`, `MEDIUM`, `HIGH`, `URGENT` |  | [Optional] [Defaults to `undefined`] [Enum: LOW, MEDIUM, HIGH, URGENT] |
| **prioritySpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **priorityIn** | `LOW`, `MEDIUM`, `HIGH`, `URGENT` |  | [Optional] [Enum: LOW, MEDIUM, HIGH, URGENT] |
| **priorityNotIn** | `LOW`, `MEDIUM`, `HIGH`, `URGENT` |  | [Optional] [Enum: LOW, MEDIUM, HIGH, URGENT] |
| **visibilityEquals** | `PUBLIC`, `PRIVATE` |  | [Optional] [Defaults to `undefined`] [Enum: PUBLIC, PRIVATE] |
| **visibilityNotEquals** | `PUBLIC`, `PRIVATE` |  | [Optional] [Defaults to `undefined`] [Enum: PUBLIC, PRIVATE] |
| **visibilitySpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **visibilityIn** | `PUBLIC`, `PRIVATE` |  | [Optional] [Enum: PUBLIC, PRIVATE] |
| **visibilityNotIn** | `PUBLIC`, `PRIVATE` |  | [Optional] [Enum: PUBLIC, PRIVATE] |
| **categoryEquals** | `ROAD_DAMAGE`, `WATER_SUPPLY`, `STREET_LIGHT`, `DRAINAGE`, `GARBAGE`, `ELECTRICITY`, `TREE`, `OTHER` |  | [Optional] [Defaults to `undefined`] [Enum: ROAD_DAMAGE, WATER_SUPPLY, STREET_LIGHT, DRAINAGE, GARBAGE, ELECTRICITY, TREE, OTHER] |
| **categoryNotEquals** | `ROAD_DAMAGE`, `WATER_SUPPLY`, `STREET_LIGHT`, `DRAINAGE`, `GARBAGE`, `ELECTRICITY`, `TREE`, `OTHER` |  | [Optional] [Defaults to `undefined`] [Enum: ROAD_DAMAGE, WATER_SUPPLY, STREET_LIGHT, DRAINAGE, GARBAGE, ELECTRICITY, TREE, OTHER] |
| **categorySpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **categoryIn** | `ROAD_DAMAGE`, `WATER_SUPPLY`, `STREET_LIGHT`, `DRAINAGE`, `GARBAGE`, `ELECTRICITY`, `TREE`, `OTHER` |  | [Optional] [Enum: ROAD_DAMAGE, WATER_SUPPLY, STREET_LIGHT, DRAINAGE, GARBAGE, ELECTRICITY, TREE, OTHER] |
| **categoryNotIn** | `ROAD_DAMAGE`, `WATER_SUPPLY`, `STREET_LIGHT`, `DRAINAGE`, `GARBAGE`, `ELECTRICITY`, `TREE`, `OTHER` |  | [Optional] [Enum: ROAD_DAMAGE, WATER_SUPPLY, STREET_LIGHT, DRAINAGE, GARBAGE, ELECTRICITY, TREE, OTHER] |
| **createdDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **createdDateIn** | `Array<Date>` |  | [Optional] |
| **createdDateNotIn** | `Array<Date>` |  | [Optional] |
| **updatedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateIn** | `Array<Date>` |  | [Optional] |
| **updatedDateNotIn** | `Array<Date>` |  | [Optional] |
| **expectedResolutionDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateIn** | `Array<Date>` |  | [Optional] |
| **expectedResolutionDateNotIn** | `Array<Date>` |  | [Optional] |
| **resolvedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateIn** | `Array<Date>` |  | [Optional] |
| **resolvedDateNotIn** | `Array<Date>` |  | [Optional] |
| **aiDuplicateEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **aiDuplicateNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **aiDuplicateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **aiDuplicateIn** | `Array<boolean>` |  | [Optional] |
| **aiDuplicateNotIn** | `Array<boolean>` |  | [Optional] |
| **duplicateScoreGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreIn** | `Array<number>` |  | [Optional] |
| **duplicateScoreNotIn** | `Array<number>` |  | [Optional] |
| **aiConfidenceGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceIn** | `Array<number>` |  | [Optional] |
| **aiConfidenceNotIn** | `Array<number>` |  | [Optional] |
| **duplicateTicketIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdIn** | `Array<number>` |  | [Optional] |
| **duplicateTicketIdNotIn** | `Array<number>` |  | [Optional] |
| **deletedEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedIn** | `Array<boolean>` |  | [Optional] |
| **deletedNotIn** | `Array<boolean>` |  | [Optional] |
| **deletedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateIn** | `Array<Date>` |  | [Optional] |
| **deletedDateNotIn** | `Array<Date>` |  | [Optional] |
| **reportedByIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdIn** | `Array<number>` |  | [Optional] |
| **reportedByIdNotIn** | `Array<number>` |  | [Optional] |
| **locationIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **locationIdIn** | `Array<number>` |  | [Optional] |
| **locationIdNotIn** | `Array<number>` |  | [Optional] |
| **wardIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **wardIdIn** | `Array<number>` |  | [Optional] |
| **wardIdNotIn** | `Array<number>` |  | [Optional] |
| **assignedDepartmentIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdIn** | `Array<number>` |  | [Optional] |
| **assignedDepartmentIdNotIn** | `Array<number>` |  | [Optional] |
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


## createTicket

> TicketDTO createTicket(ticketDTO)



### Example

```ts
import {
  Configuration,
  TicketResourceApi,
} from '';
import type { CreateTicketRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketResourceApi();

  const body = {
    // TicketDTO
    ticketDTO: ...,
  } satisfies CreateTicketRequest;

  try {
    const data = await api.createTicket(body);
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
| **ticketDTO** | [TicketDTO](TicketDTO.md) |  | |

### Return type

[**TicketDTO**](TicketDTO.md)

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


## deleteTicket

> deleteTicket(id)



### Example

```ts
import {
  Configuration,
  TicketResourceApi,
} from '';
import type { DeleteTicketRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies DeleteTicketRequest;

  try {
    const data = await api.deleteTicket(body);
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


## getAllTickets

> Array&lt;TicketDTO&gt; getAllTickets(idGreaterThan, idLessThan, idGreaterThanOrEqual, idLessThanOrEqual, idEquals, idNotEquals, idSpecified, idIn, idNotIn, titleContains, titleDoesNotContain, titleEquals, titleNotEquals, titleSpecified, titleIn, titleNotIn, statusEquals, statusNotEquals, statusSpecified, statusIn, statusNotIn, priorityEquals, priorityNotEquals, prioritySpecified, priorityIn, priorityNotIn, visibilityEquals, visibilityNotEquals, visibilitySpecified, visibilityIn, visibilityNotIn, categoryEquals, categoryNotEquals, categorySpecified, categoryIn, categoryNotIn, createdDateGreaterThan, createdDateLessThan, createdDateGreaterThanOrEqual, createdDateLessThanOrEqual, createdDateEquals, createdDateNotEquals, createdDateSpecified, createdDateIn, createdDateNotIn, updatedDateGreaterThan, updatedDateLessThan, updatedDateGreaterThanOrEqual, updatedDateLessThanOrEqual, updatedDateEquals, updatedDateNotEquals, updatedDateSpecified, updatedDateIn, updatedDateNotIn, expectedResolutionDateGreaterThan, expectedResolutionDateLessThan, expectedResolutionDateGreaterThanOrEqual, expectedResolutionDateLessThanOrEqual, expectedResolutionDateEquals, expectedResolutionDateNotEquals, expectedResolutionDateSpecified, expectedResolutionDateIn, expectedResolutionDateNotIn, resolvedDateGreaterThan, resolvedDateLessThan, resolvedDateGreaterThanOrEqual, resolvedDateLessThanOrEqual, resolvedDateEquals, resolvedDateNotEquals, resolvedDateSpecified, resolvedDateIn, resolvedDateNotIn, aiDuplicateEquals, aiDuplicateNotEquals, aiDuplicateSpecified, aiDuplicateIn, aiDuplicateNotIn, duplicateScoreGreaterThan, duplicateScoreLessThan, duplicateScoreGreaterThanOrEqual, duplicateScoreLessThanOrEqual, duplicateScoreEquals, duplicateScoreNotEquals, duplicateScoreSpecified, duplicateScoreIn, duplicateScoreNotIn, aiConfidenceGreaterThan, aiConfidenceLessThan, aiConfidenceGreaterThanOrEqual, aiConfidenceLessThanOrEqual, aiConfidenceEquals, aiConfidenceNotEquals, aiConfidenceSpecified, aiConfidenceIn, aiConfidenceNotIn, duplicateTicketIdGreaterThan, duplicateTicketIdLessThan, duplicateTicketIdGreaterThanOrEqual, duplicateTicketIdLessThanOrEqual, duplicateTicketIdEquals, duplicateTicketIdNotEquals, duplicateTicketIdSpecified, duplicateTicketIdIn, duplicateTicketIdNotIn, deletedEquals, deletedNotEquals, deletedSpecified, deletedIn, deletedNotIn, deletedDateGreaterThan, deletedDateLessThan, deletedDateGreaterThanOrEqual, deletedDateLessThanOrEqual, deletedDateEquals, deletedDateNotEquals, deletedDateSpecified, deletedDateIn, deletedDateNotIn, reportedByIdGreaterThan, reportedByIdLessThan, reportedByIdGreaterThanOrEqual, reportedByIdLessThanOrEqual, reportedByIdEquals, reportedByIdNotEquals, reportedByIdSpecified, reportedByIdIn, reportedByIdNotIn, locationIdGreaterThan, locationIdLessThan, locationIdGreaterThanOrEqual, locationIdLessThanOrEqual, locationIdEquals, locationIdNotEquals, locationIdSpecified, locationIdIn, locationIdNotIn, wardIdGreaterThan, wardIdLessThan, wardIdGreaterThanOrEqual, wardIdLessThanOrEqual, wardIdEquals, wardIdNotEquals, wardIdSpecified, wardIdIn, wardIdNotIn, assignedDepartmentIdGreaterThan, assignedDepartmentIdLessThan, assignedDepartmentIdGreaterThanOrEqual, assignedDepartmentIdLessThanOrEqual, assignedDepartmentIdEquals, assignedDepartmentIdNotEquals, assignedDepartmentIdSpecified, assignedDepartmentIdIn, assignedDepartmentIdNotIn, distinct, page, size, sort)



### Example

```ts
import {
  Configuration,
  TicketResourceApi,
} from '';
import type { GetAllTicketsRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketResourceApi();

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
    titleContains: titleContains_example,
    // string (optional)
    titleDoesNotContain: titleDoesNotContain_example,
    // string (optional)
    titleEquals: titleEquals_example,
    // string (optional)
    titleNotEquals: titleNotEquals_example,
    // boolean (optional)
    titleSpecified: true,
    // Array<string> (optional)
    titleIn: ...,
    // Array<string> (optional)
    titleNotIn: ...,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    statusEquals: statusEquals_example,
    // 'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED' (optional)
    statusNotEquals: statusNotEquals_example,
    // boolean (optional)
    statusSpecified: true,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    statusIn: ...,
    // Array<'OPEN' | 'UNDER_REVIEW' | 'APPROVED' | 'ASSIGNED' | 'IN_PROGRESS' | 'RESOLVED' | 'REJECTED' | 'CLOSED'> (optional)
    statusNotIn: ...,
    // 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT' (optional)
    priorityEquals: priorityEquals_example,
    // 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT' (optional)
    priorityNotEquals: priorityNotEquals_example,
    // boolean (optional)
    prioritySpecified: true,
    // Array<'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT'> (optional)
    priorityIn: ...,
    // Array<'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT'> (optional)
    priorityNotIn: ...,
    // 'PUBLIC' | 'PRIVATE' (optional)
    visibilityEquals: visibilityEquals_example,
    // 'PUBLIC' | 'PRIVATE' (optional)
    visibilityNotEquals: visibilityNotEquals_example,
    // boolean (optional)
    visibilitySpecified: true,
    // Array<'PUBLIC' | 'PRIVATE'> (optional)
    visibilityIn: ...,
    // Array<'PUBLIC' | 'PRIVATE'> (optional)
    visibilityNotIn: ...,
    // 'ROAD_DAMAGE' | 'WATER_SUPPLY' | 'STREET_LIGHT' | 'DRAINAGE' | 'GARBAGE' | 'ELECTRICITY' | 'TREE' | 'OTHER' (optional)
    categoryEquals: categoryEquals_example,
    // 'ROAD_DAMAGE' | 'WATER_SUPPLY' | 'STREET_LIGHT' | 'DRAINAGE' | 'GARBAGE' | 'ELECTRICITY' | 'TREE' | 'OTHER' (optional)
    categoryNotEquals: categoryNotEquals_example,
    // boolean (optional)
    categorySpecified: true,
    // Array<'ROAD_DAMAGE' | 'WATER_SUPPLY' | 'STREET_LIGHT' | 'DRAINAGE' | 'GARBAGE' | 'ELECTRICITY' | 'TREE' | 'OTHER'> (optional)
    categoryIn: ...,
    // Array<'ROAD_DAMAGE' | 'WATER_SUPPLY' | 'STREET_LIGHT' | 'DRAINAGE' | 'GARBAGE' | 'ELECTRICITY' | 'TREE' | 'OTHER'> (optional)
    categoryNotIn: ...,
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
    // Date (optional)
    updatedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    updatedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    updatedDateSpecified: true,
    // Array<Date> (optional)
    updatedDateIn: ...,
    // Array<Date> (optional)
    updatedDateNotIn: ...,
    // Date (optional)
    expectedResolutionDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    expectedResolutionDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    expectedResolutionDateSpecified: true,
    // Array<Date> (optional)
    expectedResolutionDateIn: ...,
    // Array<Date> (optional)
    expectedResolutionDateNotIn: ...,
    // Date (optional)
    resolvedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    resolvedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    resolvedDateSpecified: true,
    // Array<Date> (optional)
    resolvedDateIn: ...,
    // Array<Date> (optional)
    resolvedDateNotIn: ...,
    // boolean (optional)
    aiDuplicateEquals: true,
    // boolean (optional)
    aiDuplicateNotEquals: true,
    // boolean (optional)
    aiDuplicateSpecified: true,
    // Array<boolean> (optional)
    aiDuplicateIn: ...,
    // Array<boolean> (optional)
    aiDuplicateNotIn: ...,
    // number (optional)
    duplicateScoreGreaterThan: 1.2,
    // number (optional)
    duplicateScoreLessThan: 1.2,
    // number (optional)
    duplicateScoreGreaterThanOrEqual: 1.2,
    // number (optional)
    duplicateScoreLessThanOrEqual: 1.2,
    // number (optional)
    duplicateScoreEquals: 1.2,
    // number (optional)
    duplicateScoreNotEquals: 1.2,
    // boolean (optional)
    duplicateScoreSpecified: true,
    // Array<number> (optional)
    duplicateScoreIn: ...,
    // Array<number> (optional)
    duplicateScoreNotIn: ...,
    // number (optional)
    aiConfidenceGreaterThan: 1.2,
    // number (optional)
    aiConfidenceLessThan: 1.2,
    // number (optional)
    aiConfidenceGreaterThanOrEqual: 1.2,
    // number (optional)
    aiConfidenceLessThanOrEqual: 1.2,
    // number (optional)
    aiConfidenceEquals: 1.2,
    // number (optional)
    aiConfidenceNotEquals: 1.2,
    // boolean (optional)
    aiConfidenceSpecified: true,
    // Array<number> (optional)
    aiConfidenceIn: ...,
    // Array<number> (optional)
    aiConfidenceNotIn: ...,
    // number (optional)
    duplicateTicketIdGreaterThan: 789,
    // number (optional)
    duplicateTicketIdLessThan: 789,
    // number (optional)
    duplicateTicketIdGreaterThanOrEqual: 789,
    // number (optional)
    duplicateTicketIdLessThanOrEqual: 789,
    // number (optional)
    duplicateTicketIdEquals: 789,
    // number (optional)
    duplicateTicketIdNotEquals: 789,
    // boolean (optional)
    duplicateTicketIdSpecified: true,
    // Array<number> (optional)
    duplicateTicketIdIn: ...,
    // Array<number> (optional)
    duplicateTicketIdNotIn: ...,
    // boolean (optional)
    deletedEquals: true,
    // boolean (optional)
    deletedNotEquals: true,
    // boolean (optional)
    deletedSpecified: true,
    // Array<boolean> (optional)
    deletedIn: ...,
    // Array<boolean> (optional)
    deletedNotIn: ...,
    // Date (optional)
    deletedDateGreaterThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateLessThan: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateGreaterThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateLessThanOrEqual: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateEquals: 2013-10-20T19:20:30+01:00,
    // Date (optional)
    deletedDateNotEquals: 2013-10-20T19:20:30+01:00,
    // boolean (optional)
    deletedDateSpecified: true,
    // Array<Date> (optional)
    deletedDateIn: ...,
    // Array<Date> (optional)
    deletedDateNotIn: ...,
    // number (optional)
    reportedByIdGreaterThan: 789,
    // number (optional)
    reportedByIdLessThan: 789,
    // number (optional)
    reportedByIdGreaterThanOrEqual: 789,
    // number (optional)
    reportedByIdLessThanOrEqual: 789,
    // number (optional)
    reportedByIdEquals: 789,
    // number (optional)
    reportedByIdNotEquals: 789,
    // boolean (optional)
    reportedByIdSpecified: true,
    // Array<number> (optional)
    reportedByIdIn: ...,
    // Array<number> (optional)
    reportedByIdNotIn: ...,
    // number (optional)
    locationIdGreaterThan: 789,
    // number (optional)
    locationIdLessThan: 789,
    // number (optional)
    locationIdGreaterThanOrEqual: 789,
    // number (optional)
    locationIdLessThanOrEqual: 789,
    // number (optional)
    locationIdEquals: 789,
    // number (optional)
    locationIdNotEquals: 789,
    // boolean (optional)
    locationIdSpecified: true,
    // Array<number> (optional)
    locationIdIn: ...,
    // Array<number> (optional)
    locationIdNotIn: ...,
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
    // number (optional)
    assignedDepartmentIdGreaterThan: 789,
    // number (optional)
    assignedDepartmentIdLessThan: 789,
    // number (optional)
    assignedDepartmentIdGreaterThanOrEqual: 789,
    // number (optional)
    assignedDepartmentIdLessThanOrEqual: 789,
    // number (optional)
    assignedDepartmentIdEquals: 789,
    // number (optional)
    assignedDepartmentIdNotEquals: 789,
    // boolean (optional)
    assignedDepartmentIdSpecified: true,
    // Array<number> (optional)
    assignedDepartmentIdIn: ...,
    // Array<number> (optional)
    assignedDepartmentIdNotIn: ...,
    // boolean (optional)
    distinct: true,
    // number | Zero-based page index (0..N) (optional)
    page: 56,
    // number | The size of the page to be returned (optional)
    size: 56,
    // Array<string> | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. (optional)
    sort: ...,
  } satisfies GetAllTicketsRequest;

  try {
    const data = await api.getAllTickets(body);
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
| **titleContains** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleDoesNotContain** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleNotEquals** | `string` |  | [Optional] [Defaults to `undefined`] |
| **titleSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **titleIn** | `Array<string>` |  | [Optional] |
| **titleNotIn** | `Array<string>` |  | [Optional] |
| **statusEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **statusNotEquals** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Defaults to `undefined`] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **statusSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **statusIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **statusNotIn** | `OPEN`, `UNDER_REVIEW`, `APPROVED`, `ASSIGNED`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |  | [Optional] [Enum: OPEN, UNDER_REVIEW, APPROVED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED] |
| **priorityEquals** | `LOW`, `MEDIUM`, `HIGH`, `URGENT` |  | [Optional] [Defaults to `undefined`] [Enum: LOW, MEDIUM, HIGH, URGENT] |
| **priorityNotEquals** | `LOW`, `MEDIUM`, `HIGH`, `URGENT` |  | [Optional] [Defaults to `undefined`] [Enum: LOW, MEDIUM, HIGH, URGENT] |
| **prioritySpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **priorityIn** | `LOW`, `MEDIUM`, `HIGH`, `URGENT` |  | [Optional] [Enum: LOW, MEDIUM, HIGH, URGENT] |
| **priorityNotIn** | `LOW`, `MEDIUM`, `HIGH`, `URGENT` |  | [Optional] [Enum: LOW, MEDIUM, HIGH, URGENT] |
| **visibilityEquals** | `PUBLIC`, `PRIVATE` |  | [Optional] [Defaults to `undefined`] [Enum: PUBLIC, PRIVATE] |
| **visibilityNotEquals** | `PUBLIC`, `PRIVATE` |  | [Optional] [Defaults to `undefined`] [Enum: PUBLIC, PRIVATE] |
| **visibilitySpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **visibilityIn** | `PUBLIC`, `PRIVATE` |  | [Optional] [Enum: PUBLIC, PRIVATE] |
| **visibilityNotIn** | `PUBLIC`, `PRIVATE` |  | [Optional] [Enum: PUBLIC, PRIVATE] |
| **categoryEquals** | `ROAD_DAMAGE`, `WATER_SUPPLY`, `STREET_LIGHT`, `DRAINAGE`, `GARBAGE`, `ELECTRICITY`, `TREE`, `OTHER` |  | [Optional] [Defaults to `undefined`] [Enum: ROAD_DAMAGE, WATER_SUPPLY, STREET_LIGHT, DRAINAGE, GARBAGE, ELECTRICITY, TREE, OTHER] |
| **categoryNotEquals** | `ROAD_DAMAGE`, `WATER_SUPPLY`, `STREET_LIGHT`, `DRAINAGE`, `GARBAGE`, `ELECTRICITY`, `TREE`, `OTHER` |  | [Optional] [Defaults to `undefined`] [Enum: ROAD_DAMAGE, WATER_SUPPLY, STREET_LIGHT, DRAINAGE, GARBAGE, ELECTRICITY, TREE, OTHER] |
| **categorySpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **categoryIn** | `ROAD_DAMAGE`, `WATER_SUPPLY`, `STREET_LIGHT`, `DRAINAGE`, `GARBAGE`, `ELECTRICITY`, `TREE`, `OTHER` |  | [Optional] [Enum: ROAD_DAMAGE, WATER_SUPPLY, STREET_LIGHT, DRAINAGE, GARBAGE, ELECTRICITY, TREE, OTHER] |
| **categoryNotIn** | `ROAD_DAMAGE`, `WATER_SUPPLY`, `STREET_LIGHT`, `DRAINAGE`, `GARBAGE`, `ELECTRICITY`, `TREE`, `OTHER` |  | [Optional] [Enum: ROAD_DAMAGE, WATER_SUPPLY, STREET_LIGHT, DRAINAGE, GARBAGE, ELECTRICITY, TREE, OTHER] |
| **createdDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **createdDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **createdDateIn** | `Array<Date>` |  | [Optional] |
| **createdDateNotIn** | `Array<Date>` |  | [Optional] |
| **updatedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **updatedDateIn** | `Array<Date>` |  | [Optional] |
| **updatedDateNotIn** | `Array<Date>` |  | [Optional] |
| **expectedResolutionDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **expectedResolutionDateIn** | `Array<Date>` |  | [Optional] |
| **expectedResolutionDateNotIn** | `Array<Date>` |  | [Optional] |
| **resolvedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **resolvedDateIn** | `Array<Date>` |  | [Optional] |
| **resolvedDateNotIn** | `Array<Date>` |  | [Optional] |
| **aiDuplicateEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **aiDuplicateNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **aiDuplicateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **aiDuplicateIn** | `Array<boolean>` |  | [Optional] |
| **aiDuplicateNotIn** | `Array<boolean>` |  | [Optional] |
| **duplicateScoreGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **duplicateScoreIn** | `Array<number>` |  | [Optional] |
| **duplicateScoreNotIn** | `Array<number>` |  | [Optional] |
| **aiConfidenceGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **aiConfidenceIn** | `Array<number>` |  | [Optional] |
| **aiConfidenceNotIn** | `Array<number>` |  | [Optional] |
| **duplicateTicketIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **duplicateTicketIdIn** | `Array<number>` |  | [Optional] |
| **duplicateTicketIdNotIn** | `Array<number>` |  | [Optional] |
| **deletedEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedNotEquals** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedIn** | `Array<boolean>` |  | [Optional] |
| **deletedNotIn** | `Array<boolean>` |  | [Optional] |
| **deletedDateGreaterThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateLessThan** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateGreaterThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateLessThanOrEqual** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateNotEquals** | `Date` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **deletedDateIn** | `Array<Date>` |  | [Optional] |
| **deletedDateNotIn** | `Array<Date>` |  | [Optional] |
| **reportedByIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **reportedByIdIn** | `Array<number>` |  | [Optional] |
| **reportedByIdNotIn** | `Array<number>` |  | [Optional] |
| **locationIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **locationIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **locationIdIn** | `Array<number>` |  | [Optional] |
| **locationIdNotIn** | `Array<number>` |  | [Optional] |
| **wardIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **wardIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **wardIdIn** | `Array<number>` |  | [Optional] |
| **wardIdNotIn** | `Array<number>` |  | [Optional] |
| **assignedDepartmentIdGreaterThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdLessThan** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdGreaterThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdLessThanOrEqual** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdNotEquals** | `number` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdSpecified** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **assignedDepartmentIdIn** | `Array<number>` |  | [Optional] |
| **assignedDepartmentIdNotIn** | `Array<number>` |  | [Optional] |
| **distinct** | `boolean` |  | [Optional] [Defaults to `undefined`] |
| **page** | `number` | Zero-based page index (0..N) | [Optional] [Defaults to `0`] |
| **size** | `number` | The size of the page to be returned | [Optional] [Defaults to `20`] |
| **sort** | `Array<string>` | Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported. | [Optional] |

### Return type

[**Array&lt;TicketDTO&gt;**](TicketDTO.md)

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


## getTicket

> TicketDTO getTicket(id)



### Example

```ts
import {
  Configuration,
  TicketResourceApi,
} from '';
import type { GetTicketRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketResourceApi();

  const body = {
    // number
    id: 789,
  } satisfies GetTicketRequest;

  try {
    const data = await api.getTicket(body);
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

[**TicketDTO**](TicketDTO.md)

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


## partialUpdateTicket

> TicketDTO partialUpdateTicket(id, ticketDTO)



### Example

```ts
import {
  Configuration,
  TicketResourceApi,
} from '';
import type { PartialUpdateTicketRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketResourceApi();

  const body = {
    // number
    id: 789,
    // TicketDTO
    ticketDTO: ...,
  } satisfies PartialUpdateTicketRequest;

  try {
    const data = await api.partialUpdateTicket(body);
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
| **ticketDTO** | [TicketDTO](TicketDTO.md) |  | |

### Return type

[**TicketDTO**](TicketDTO.md)

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


## updateTicket

> TicketDTO updateTicket(id, ticketDTO)



### Example

```ts
import {
  Configuration,
  TicketResourceApi,
} from '';
import type { UpdateTicketRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TicketResourceApi();

  const body = {
    // number
    id: 789,
    // TicketDTO
    ticketDTO: ...,
  } satisfies UpdateTicketRequest;

  try {
    const data = await api.updateTicket(body);
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
| **ticketDTO** | [TicketDTO](TicketDTO.md) |  | |

### Return type

[**TicketDTO**](TicketDTO.md)

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

