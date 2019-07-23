<#include "security.ftl">

<#macro login>
<form action="/login" method="post" class="mt-5">

  <div class="form-group row">
    <label class="col-sm-2 col-form-label"> User Name :</label>
    <div class="col-sm-6">
      <input type="text" class="form-control" name="username"/>
    </div>
  </div>

  <div class="form-group row">
    <label class="col-sm-2 col-form-label"> Password:</label>
    <div class="col-sm-6">
      <input type="password" class="form-control" name="password"/>
    </div>
  </div>

  <input type="hidden" name="_csrf" value="${_csrf.token}" />


  <div class="form-group row">
    <div class="col-sm-10">
      <input type="submit" value="Sign In" class="btn btn-primary"/>
    </div>
  </div>
</form>
</#macro>



<#macro logout>
<form action="/logout" method="post">
  <input type="hidden" name="_csrf" value="${_csrf.token}" />
  <button type="submit" class="btn btn-primary"><#if user??>Sign Out<#else>Log In</#if></button>
</form>
</#macro>



<#macro registration>
<form action="/registration" class="mt-3" method="post">

  <div class="form-group row">
    <label class="col-sm-2 col-form-label"> Name : </label>
    <div class="col-sm-6">
      <input class="form-control ${(nameError??)?string('is-invalid', '')}" type="text" name="name" value="<#if user??>${user.name}</#if>"/>
      <#if nameError??>
      <div class="invalid-feedback">
        ${nameError}
      </div>
    </#if>
  </div>
  </div>

  <div class="form-group row">
    <label class="col-sm-2 col-form-label">Username : </label>
    <div class="col-sm-6">
      <input class="form-control ${(usernameError??)?string('is-invalid', '')}" type="text" name="username" value="<#if user??>${user.username}</#if>"/>
      <#if usernameError??>
      <div class="invalid-feedback">
        ${usernameError}
      </div>
    </#if>
  </div>
  </div>

  <div class="form-group row">
    <label class="col-sm-2 col-form-label">Email : </label>
    <div class="col-sm-6">
      <input class="form-control ${(emailError??)?string('is-invalid', '')}" type="email" name="email" value="<#if user??>${user.email}</#if>"/>
      <#if emailError??>
      <div class="invalid-feedback">
        ${emailError}
      </div>
    </#if>
  </div>
  </div>

  <div class="form-group row">
    <label class="col-sm-2 col-form-label"> Password: </label>
    <div class="col-sm-6">
      <input class="form-control ${(passwordError??)?string('is-invalid', '')}" type="password" name="password"/>
      <#if passwordError??>
      <div class="invalid-feedback">
        ${passwordError}
      </div>
    </#if>
  </div>
  </div>

  <div class="form-group row">
    <label class="col-sm-2 col-form-label">Confirm password: </label>
    <div class="col-sm-6">
      <input class="form-control ${(confirmPasswordError??)?string('is-invalid', '')}" type="password" name="confirmPassword"/>
      <#if confirmPasswordError??>
      <div class="invalid-feedback">
        ${confirmPasswordError}
      </div>
    </#if>
  </div>
  </div>

  <div class="col-sm-6">
    <div class="g-recaptcha" data-sitekey="6LeA3a0UAAAAAIAk6EqGv8H0Zx3sE2ox9Ej1pjUF"></div>
    <#if captchaError??>
    <div class="alert alert-danger" role="alert">
      ${captchaError}
    </div>
  </#if>
  </div>

  <input type="hidden" name="_csrf" value="${_csrf.token}" />

  <div class="form-group row">
    <input type="submit" class="btn btn-primary" value="Sign Up"/>
  </div>
</form>
</#macro>
