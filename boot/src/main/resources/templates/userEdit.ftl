<#import "parts/common.ftl" as c>

<@c.page>
<form action="/user" method="post" >
  <input type="text" value="${user.username}" name="username">
  <input type="text" value="${user.name}" name="name">
  <#list roles as role>
    <div>
      <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
    </div>
  </#list>
<input type="hidden" value="${user.id}" name="id">
<input type="hidden" value="${_csrf.token}" name="_csrf">
<button type="submit">Update</button>
</form>
</@c.page>
