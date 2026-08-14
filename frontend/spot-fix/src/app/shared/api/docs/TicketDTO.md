
# TicketDTO


## Properties

Name | Type
------------ | -------------
`id` | number
`title` | string
`description` | string
`status` | string
`priority` | string
`visibility` | string
`category` | string
`createdDate` | Date
`updatedDate` | Date
`expectedResolutionDate` | Date
`resolvedDate` | Date
`aiSummary` | string
`aiDuplicate` | boolean
`duplicateScore` | number
`aiConfidence` | number
`duplicateTicketId` | number
`deleted` | boolean
`deletedDate` | Date
`reportedBy` | [UserDTO](UserDTO.md)
`location` | [LocationDTO](LocationDTO.md)
`ward` | [WardDTO](WardDTO.md)
`assignedDepartment` | [DepartmentDTO](DepartmentDTO.md)

## Example

```typescript
import type { TicketDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "title": null,
  "description": null,
  "status": null,
  "priority": null,
  "visibility": null,
  "category": null,
  "createdDate": null,
  "updatedDate": null,
  "expectedResolutionDate": null,
  "resolvedDate": null,
  "aiSummary": null,
  "aiDuplicate": null,
  "duplicateScore": null,
  "aiConfidence": null,
  "duplicateTicketId": null,
  "deleted": null,
  "deletedDate": null,
  "reportedBy": null,
  "location": null,
  "ward": null,
  "assignedDepartment": null,
} satisfies TicketDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as TicketDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


