<a class="btn btn-primary" data-toggle="collapse" href="#collapseEdit" role="button" aria-expanded="false" aria-controls="collapseEdit">
  Message Editor
</a>

<div class="collapse <#if message??>show</#if>" id="collapseEdit">
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
<input type="hidden" name="_csrf" value="${_csrf.token}" />
<input type="hidden" name="id" value="<#if message??>${message.id}</#if>" />
<button type="submit"  class="btn btn-primary">Save</button>
</form>
</div>
</div>
