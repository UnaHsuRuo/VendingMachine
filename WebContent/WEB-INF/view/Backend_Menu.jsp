<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
.btn {
	  --bs-btn-padding-x: 1.1rem;
	  --bs-btn-padding-y: 0.5rem;
	  --bs-btn-font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Helvetica Neue, Arial, Noto Sans, sans-serif, Apple Color Emoji, Segoe UI Emoji, Segoe UI Symbol, Noto Color Emoji;
	  --bs-btn-font-size: 0.875rem;
	  --bs-btn-font-weight: 400;
	  --bs-btn-line-height: 1.5;
	  --bs-btn-color: #343a40;
	  --bs-btn-bg: transparent;
	  --bs-btn-border-width: 1px;
	  --bs-btn-border-color: transparent;
	  --bs-btn-border-radius: 1.078em;
	  --bs-btn-hover-border-color: transparent;
	  --bs-btn-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.15), 0 1px 1px rgba(0, 0, 0, 0.075);
	  --bs-btn-disabled-opacity: 0.65;
	  --bs-btn-focus-box-shadow: 0 0 0 0.25rem rgba(var(--bs-btn-focus-shadow-rgb), .5);
	  display: inline-block;
	  padding: var(--bs-btn-padding-y) var(--bs-btn-padding-x);
	  font-family: var(--bs-btn-font-family);
	  font-size: var(--bs-btn-font-size);
	  font-weight: var(--bs-btn-font-weight);
	  line-height: var(--bs-btn-line-height);
	  color: var(--bs-btn-color);
	  text-align: center;
	  text-decoration: none;
	  vertical-align: middle;
	  cursor: pointer;
	  -webkit-user-select: none;
	  -moz-user-select: none;
	  user-select: none;
	  border: var(--bs-btn-border-width) solid var(--bs-btn-border-color);
	  border-radius: var(--bs-btn-border-radius);
	  background-color: var(--bs-btn-bg);
	  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
	}
	@media (prefers-reduced-motion: reduce) {
	  .btn {
	    transition: none;
	  }
	}
	.btn:hover {
	  color: var(--bs-btn-hover-color);
	  background-color: var(--bs-btn-hover-bg);
	  border-color: var(--bs-btn-hover-border-color);
	}
	.btn-check + .btn:hover {
	  color: var(--bs-btn-color);
	  background-color: var(--bs-btn-bg);
	  border-color: var(--bs-btn-border-color);
	}
	.btn:focus-visible {
	  color: var(--bs-btn-hover-color);
	  background-color: var(--bs-btn-hover-bg);
	  border-color: var(--bs-btn-hover-border-color);
	  outline: 0;
	  box-shadow: var(--bs-btn-focus-box-shadow);
	}
	.btn-check:focus-visible + .btn {
	  border-color: var(--bs-btn-hover-border-color);
	  outline: 0;
	  box-shadow: var(--bs-btn-focus-box-shadow);
	}
	.btn-check:checked + .btn, :not(.btn-check) + .btn:active, .btn:first-child:active, .btn.active, .btn.show {
	  color: var(--bs-btn-active-color);
	  background-color: var(--bs-btn-active-bg);
	  border-color: var(--bs-btn-active-border-color);
	}
	.btn-check:checked + .btn:focus-visible, :not(.btn-check) + .btn:active:focus-visible, .btn:first-child:active:focus-visible, .btn.active:focus-visible, .btn.show:focus-visible {
	  box-shadow: var(--bs-btn-focus-box-shadow);
	}
	.btn:disabled, .btn.disabled, fieldset:disabled .btn {
	  color: var(--bs-btn-disabled-color);
	  pointer-events: none;
	  background-color: var(--bs-btn-disabled-bg);
	  border-color: var(--bs-btn-disabled-border-color);
	  opacity: var(--bs-btn-disabled-opacity);
	}
	
	
.btn-outline-dark {
  --bs-btn-color: #343a40;
  --bs-btn-border-color: #343a40;
  --bs-btn-hover-color: #fff;
  --bs-btn-hover-bg: #343a40;
  --bs-btn-hover-border-color: #343a40;
  --bs-btn-focus-shadow-rgb: 52, 58, 64;
  --bs-btn-active-color: #fff;
  --bs-btn-active-bg: #343a40;
  --bs-btn-active-border-color: #343a40;
  --bs-btn-active-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
  --bs-btn-disabled-color: #343a40;
  --bs-btn-disabled-bg: transparent;
  --bs-btn-disabled-border-color: #343a40;
  --bs-gradient: none;
}
</style>


	<h1>Vending Machine Backend Service</h1><br/>	
	<table style="border-collapse:collapse;margin: 0 auto;">
		<tr>
			<td width="150" height="50" align="center">
			<a class="btn btn-outline-dark" href="BackendAction.do?action=queryGoods&amp;pageNo=1">商品列表</a>
			</td>
			<td width="150" height="50" align="center">
				<a class="btn btn-outline-dark" href="BackendAction.do?action=modifyGoodsView">商品補貨作業</a>
			</td>
			<td width="150" height="50" align="center">
				<a class="btn btn-outline-dark" href="BackendAction.do?action=createGoodsView">商品新增上架</a>
			</td>
			<td width="150" height="50" align="center">
				<a class="btn btn-outline-dark" href="BackendAction.do?action=querySalesReport">銷售報表</a>
			</td>
			<td width="150" height="50" align="center">
				<a class="btn btn-outline-dark" href="FrontendAction.do?pageNo=1&amp;action=searchGoods&amp;searchKeyword=">前台販賣機</a>
			</td>
		</tr>
	</table>
