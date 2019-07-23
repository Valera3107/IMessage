<#import "parts/common.ftl" as c>
<@c.page>

${username}
${text?ifExists}
<form class="mt-3" method="post">

  <div class="form-group row">
    <label class="col-sm-2 col-form-label"> Name : </label>
    <div class="col-sm-6">
      <input class="form-control" type="text" name="name" placeholder="name"/>
    </div>
  </div>

  <div class="form-group row">
    <label class="col-sm-2 col-form-label">Username : </label>
    <div class="col-sm-6">
      <input class="form-control" type="text" name="username" placeholder="username"/>
    </div>
  </div>

  <div class="form-group row">
    <label class="col-sm-2 col-form-label">Email : </label>
    <div class="col-sm-6">
      <input class="form-control" type="email" name="email" placeholder="email"/>
    </div>
  </div>

  <input type="hidden" name="_csrf" value="${_csrf.token}" />

  <div class="form-group row">
    <input type="submit" class="btn btn-primary" value="Save"/>
  </div>
</form>
</@c.page>
