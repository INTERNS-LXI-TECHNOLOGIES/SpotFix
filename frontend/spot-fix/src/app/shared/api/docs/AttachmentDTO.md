
# AttachmentDTO


## Properties

Name | Type
------------ | -------------
`id` | number
`attachmentType` | string
`fileName` | string
`filePath` | string
`fileType` | string
`fileSize` | number
`checksum` | string
`uploadedDate` | Date
`transcript` | string
`durationSeconds` | number
`language` | string
`deleted` | boolean
`updatedDate` | Date
`deletedDate` | Date
`ticket` | [TicketDTO](TicketDTO.md)
`uploadedBy` | [UserDTO](UserDTO.md)

## Example

```typescript
import type { AttachmentDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "attachmentType": null,
  "fileName": null,
  "filePath": null,
  "fileType": null,
  "fileSize": null,
  "checksum": null,
  "uploadedDate": null,
  "transcript": null,
  "durationSeconds": null,
  "language": null,
  "deleted": null,
  "updatedDate": null,
  "deletedDate": null,
  "ticket": null,
  "uploadedBy": null,
} satisfies AttachmentDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as AttachmentDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


