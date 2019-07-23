<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>

<div class="form-row">
  <div class="form-group col-md-6">
    <form method="get" action="/main" class="form-inline">
      <input class="form-control mr-3" type="text" name="filter" placeholder="Input tag" value="${filter?ifExists}"/>
      <button type="submit" class="btn btn-primary">Apply</button>
    </form>
  </div>
</div>

<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
  Add new message
</a>

<div class="collapse <#if message??>show</#if>" id="collapseExample">
  <div class="form-group mt-3">
    <form method="post" enctype="multipart/form-data">

      <div class="form-group">
        <input class="form-control ${(tagError??)?string('is-invalid', '')}"
               value="<#if message??>${message.tag}</#if>" name="tag" type="text" placeholder="Input tag"/>
        <#if tagError??>
        <div class="invalid-feedback">
          ${tagError}
        </div>
      </#if>
      </div>
      <div class="form-group">
        <input class="form-control ${(textError??)?string('is-invalid', '')}"
             value="<#if message??>${message.text}</#if>"  name="text" type="text" placeholder="Input text"/>
        <#if textError??>
        <div class="invalid-feedback">
          ${textError}
        </div>
      </#if>
      </div>
      <div class="form-group">
        <div class="custom-file">
          <input id="customFile" type="file" name="image"/>
          <label class="custom-file-label" for="customFile">Choose file</label>
        </div>
      </div>
      <input class="form-control" type="hidden" name="_csrf" value="${_csrf.token}" />
      <button type="submit"  class="btn btn-primary">Send</button>
    </form>
  </div>
</div>

<#include "parts/messageList.ftl"/>
</@c.page>
