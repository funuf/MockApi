{
    "id":${mockapi.integer.single},
    "name":"${mockapi.text.name}",
    "age":${mockapi.integer.multi2[1]},
    "photo":[
        <#list mockapi.image.boys as photo>
            "${photo}"<#sep>,</#sep>
        </#list>
    ]
}