<#include "security.ftl" />
<#import "pager.ftl" as p />


<@p.pager url page />

<div class="card-columns" id="message-list">
  <#list page.content as mes>
  <div class="card my-3" style="width: 18rem;" data-id="${mes.id}">
    <#if mes.file??>
    <img class="card-img-top" src="data:image/jpg;base64, ${mes.file}"/>
  </#if>
  <div class="m-2">
    <h5 class="card-title">#${mes.tag}</h5>
    <p class="card-text">${mes.text}</p>
  </div>
  <div class="row">
    <a href="/user-messages/${mes.userId}" class=" col footer-text" style="display: block;">
      ${mes.peopleName}
    </a>
    <#if mes.userId == currentUserId>
    <a href="/user-messages/${mes.userId}?mes=${mes.id}" class="btn btn-primary" style="display: block;">
      Edit
    </a>
    </#if>
  </div>
</div>
<#else>
No messages
</#list>
</div>

<@p.pager url page />
