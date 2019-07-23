<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<dt class="text-center text-primary">Registration user</dt>
${text?ifExists}

<@l.registration />

</@c.page>
