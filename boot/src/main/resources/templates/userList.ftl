<#import "parts/common.ftl" as c>

<@c.page>
List of users
<table>
  <thead>
  <tr>
    <th>Name</th>
    <th>Role</th>
    <th>Func</th>
  </tr>
  </thead>
  <tbody>
  <#list users as u>
  <tr>
    <td>${u.name}</td>
    <td><#list u.roles as r>${r}<#sep>, </#list></td>
    <td><a href="/user/${u.id}">edit</a></td>
  </tr>
  </#list>
  </tbody>
</table>
</@c.page>
