<#import "parts/common.ftl" as c>

<@c.page>
<h3>${userChannel.name}</h3>

<#if !isCurrentUser>
  <#if isSubscribe>
    <a class="btn btn-warning" href="/user/unsubscribe/${userChannel.id}">Unsubscribe</a>
  <#else>
    <a class="btn btn-warning" href="/user/subscribe/${userChannel.id}">Subscribe</a>
  </#if>
</#if>

<div class="container my-3">
  <div class="row">
    <div class="col">
      <div class="card">
        <div class="card-body">
          <div class="card-title">Subscriptions</div>
          <h3 class="card-text">
          <a href="/user/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
          </h3>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="card">
        <div class="card-body">
          <div class="card-title">Subscribers</div>
          <h3 class="card-text">
          <a href="/user/subscribers/${userChannel.id}/list">${subscribersCount}</a>
          </h3>
        </div>
      </div>
    </div>
  </div>
</div>

<#if isCurrentUser>
  <#include "parts/messageEdit.ftl"/>
</#if>

<#include "parts/messageList.ftl"/>
</@c.page>
