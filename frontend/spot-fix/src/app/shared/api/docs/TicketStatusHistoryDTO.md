
# TicketStatusHistoryDTO


## Properties

Name | Type
------------ | -------------
`id` | number
`oldStatus` | string
`newStatus` | string
`changedDate` | Date
`ticket` | [TicketDTO](TicketDTO.md)
`changedBy` | [UserDTO](UserDTO.md)

## Example

```typescript
import type { TicketStatusHistoryDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "oldStatus": null,
  "newStatus": null,
  "changedDate": null,
  "ticket": null,
  "changedBy": null,
} satisfies TicketStatusHistoryDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as TicketStatusHistoryDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


