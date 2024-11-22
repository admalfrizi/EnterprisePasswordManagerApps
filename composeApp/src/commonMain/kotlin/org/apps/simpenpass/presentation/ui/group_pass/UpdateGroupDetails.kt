package org.apps.simpenpass.presentation.ui.group_pass

//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun UpdateGroupDetails(
//    onDismissRequest: () -> Unit,
//    isPopUp: MutableState<Boolean>,
//    scope: CoroutineScope,
//    urlImages: String,
//    groupViewModel: GroupDetailsViewModel,
//    imagesName: String?,
//    groupState: GroupDetailsState
//) {
//
//
//    if(groupState.isLoading){
//        popUpLoading(isDismiss)
//    }
//
//    if(groupState.isUpdated && !groupState.isLoading){
//        isPopUp.value = false
//        grupName = ""
//        desc = ""
//        groupViewModel.getDetailGroup(groupState.groupId!!)
//    }
//
//    Dialog(
//        onDismissRequest = {onDismissRequest()},
//        properties = DialogProperties(
//            usePlatformDefaultWidth = false
//        )
//    ){
//        Card(
//            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
//            shape = RoundedCornerShape(20.dp),
//            elevation = 0.dp,
//        ) {
//            Column(
//                modifier = Modifier.fillMaxWidth().padding(24.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        "Edit Data Grup",
//                        style = MaterialTheme.typography.h6.copy(color = secondaryColor),
//                    )
//                    IconButton(
//                        onClick = {
//                            onDismissRequest()
//                        },
//                        content = {
//                            Icon(
//                                Icons.Filled.Clear,
//                                ""
//                            )
//                        }
//                    )
//                }
//
//            }
//        }
//    }
//
//}