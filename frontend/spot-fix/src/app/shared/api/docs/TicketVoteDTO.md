
# TicketVoteDTO


## Properties

Name | Type
------------ | -------------
`id` | number
`voteType` | string
`createdDate` | Date
`ticket` | [TicketDTO](TicketDTO.md)
`user` | [UserDTO](UserDTO.md)

## Example

```typescript
import type { TicketVoteDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "voteType": null,
  "createdDate": null,
  "ticket": null,
  "user": null,
} satisfies TicketVoteDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as TicketVoteDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


