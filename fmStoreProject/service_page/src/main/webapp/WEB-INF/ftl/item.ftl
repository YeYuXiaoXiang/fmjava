<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<title>【华为nova 5 Pro】华为 HUAWEI nova 5 Pro</title>
	<link rel="icon" href="img/favicon.ico">
	<link rel="stylesheet" type="text/css" href="css/base.css" />
	<link rel="stylesheet" type="text/css" href="css/item.css" />
	<link rel="stylesheet" type="text/css" href="css/zoom.css" />
</head>
	<script>
		var skuList=[
			<#list itemList as item>
			{
				id:${item.id?c},
				title:'${item.title}',
				price:${item.price?c},
				spec:${item.spec}
			} ,
			</#list>
		];
	</script>

<body>
<#--图片列表-->
<#assign imageList=goodsDesc.itemImages?eval>
<#--规格-->
<#assign specificationList=goodsDesc.specificationItems?eval>

<div id="app">

	<!--页面顶部 开始-->
	<#include "header.ftl">
	<#--中间内容-->
	<!--主体内容-->
	<div class="py-container">
		<div id="item">
			<div class="crumb-wrap">
				<ul class="sui-breadcrumb">
					<li>
						<a href="#">${itemCat1}</a>
					</li>
					<li>
						<a href="#">${itemCat2}</a>
					</li>
					<li>
						<a href="#">${itemCat3}</a>
					</li>
				</ul>
			</div>
			<!--product-info-->
			<div class="product-info">
				<div class="fl preview-wrap">
					<!--放大镜效果-->
					<div class="zoom">
						<#if (imageList?size>0)>
							<img jqimg="${imageList[0].url}" src="${imageList[0].url}" width="400px" height="400px" />
						</#if>
						<!--下方的缩略图-->
						<div class="spec-scroll">
							<a class="prev">&lt;</a>
							<!--左右按钮-->
							<div class="items">
								<ul>
									<#list imageList as item>
										<li><img src="${item.url}" bimg="${item.url}" onmousemove="preview(this)" /></li>
									</#list>
								</ul>
							</div>
							<a class="next">&gt;</a>
						</div>
					</div>
				</div>
				<div class="fr itemInfo-wrap">
					<div class="sku-name">
						<h4>{{sku.title}}
					</div>
					<div class="news"><span>${goods.caption}</span></div>
					<div class="summary">
						<div class="summary-wrap">
							<div class="fl title">
								<i>价　　格</i>
							</div>
							<div class="fl price">
								<i>¥</i>
								<em>{{sku.price}}</em>
								<span>降价通知</span>
							</div>
							<div class="fr remark">
								<div id="comment-count" class="comment-count item fl" style="margin-right: 20px;">
									<p class="comment" style="margin-bottom: 0px;font-size: 14px;">累计评价</p>
									<a class="count" href="#none" style="font-size: 18px;">4.9万+</a>
								</div>
							</div>
						</div>
						<div class="summary-wrap">
							<div class="fl title">
								<i>促　　销</i>
							</div>
							<div class="fl fix-width">
								<i class="goods-icons4 J-picon-tips" style="margin-top: 6px; margin-right: 7px;">满送活动</i>
								<em class="t-gray">满1000.00送200.00元，或满2000.00送500.00元</em>
							</div>
						</div>
					</div>
					<div class="support">
						<div class="summary-wrap">
							<div class="fl title">
								<i>增值业务</i>
							</div>
							<div class="fl fix-width">
								<em class="t-gray">高价回收, 卖了赚钱</em>
							</div>
						</div>
						<div class="summary-wrap">
							<div class="fl title">
								<i>配 送 至</i>
							</div>
							<div class="fl fix-width">
								<em class="t-gray">江苏南京市雨花台区 有货支持
									次日达 自提 免举证退换货 原厂维修</em>
							</div>
						</div>
					</div>
					<div class="clearfix choose">
						<div id="specification" class="summary-wrap clearfix">
							<#list specificationList as spec>
								<dl>
									<dt>
										<div class="fl title">
											<i>${spec.specName}</i>
										</div>
									</dt>
									<#list spec.specOptions as item>
										<dd>
											<a href="javascript:;" :class="isSelected('${spec.specName}','${item}')?'selected':''"
											   @click="selectSpecification('${spec.specName}','${item}')"
											>
												${item}<span title="点击取消选择">&nbsp;</span>
											</a>
										</dd>
									</#list>
								</dl>
							</#list>

						</div>

						<div class="summary-wrap">
							<div class="fl title">
								<div class="control-group">
									<div class="controls">
										<input autocomplete="off" type="text" value="1" minnum="1" class="itxt" />
										<a href="javascript:void(0)" class="increment plus">+</a>
										<a href="javascript:void(0)" class="increment mins">-</a>
									</div>
								</div>
							</div>
							<div class="fl">
								<ul class="btn-choose unstyled">
									<li>
										<a href="cart.html" target="_blank" class="sui-btn  btn-danger addshopcar">加入购物车</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--商品详情-->
			<div class="clearfix product-detail">
				<div class="fl aside">
					<div class="mt_xg">
						<h3>达人选购</h3>
					</div>
					<div class="tab-content tab-wraped">
						<div id="index" class="tab-pane active">

							<ul class="goods-list unstyled">
								<li>
									<div class="list-wrap">
										<div class="p-img">
											<img src="img/daren1.jpg"  width="153" height="117"/>
										</div>
										<div class="attr">
											<em>华为 nova 5 Pro 手机全网通 绮境森林 8G+128G</em>
										</div>
										<div class="price" style="margin-top: 5px;">
											<strong>
												<em>¥</em>
												<i>2999.00</i>
											</strong>
										</div>
									</div>
								</li>
								<li>
									<div class="list-wrap">
										<div class="p-img">
											<img src="img/daren2.jpg"  width="153" height="117"/>
										</div>
										<div class="attr">
											<em>vivo iQOO Neo 骁龙845处理器 4500mAh强悍续航 22.5W超快闪充 </em>
										</div>
										<div class="price"  style="margin-top: 5px;">
											<strong>
												<em>¥</em>
												<i>1998.00</i>
											</strong>
										</div>
									</div>
								</li>
								<li>
									<div class="list-wrap">
										<div class="p-img">
											<img src="img/daren3.jpg"  width="153" height="117"/>
										</div>
										<div class="attr">
											<em>华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟</em>
										</div>
										<div class="price" style="margin-top: 5px;">
											<strong>
												<em>¥</em>
												<i>2999.00</i>
											</strong>
										</div>

									</div>
									<div class="list-wrap">
										<div class="p-img">
											<img src="img/daren4.png"  width="153" height="117"/>
										</div>
										<div class="attr">
											<em>realme X 4800万双摄 升降摄像头 屏下指纹 游戏手机 8GB+128GB朋</em>
										</div>
										<div class="price" style="margin-top: 5px;">
											<strong>
												<em>¥</em>
												<i>1799.00</i>
											</strong>
										</div>

									</div>
									<div class="list-wrap">
										<div class="p-img">
											<img src="img/daren5.jpg"  width="153" height="117"/>
										</div>
										<div class="attr">
											<em>荣耀20 4800万超广角AI四摄 3200W美颜自拍 麒麟Kirin980全网</em>
										</div>
										<div class="price" style="margin-top: 5px;">
											<strong>
												<em>¥</em>
												<i>2699.00</i>
											</strong>
										</div>

									</div>
								</li>
							</ul>
						</div>
						<div id="profile" class="tab-pane">
							<p>推荐品牌</p>
						</div>
					</div>
				</div>
				<div class="fr detail">

					<div class="tab-main intro">
						<ul class="sui-nav nav-tabs tab-wraped">
							<li class="active">
								<a href="#one" data-toggle="tab">
									商品介绍
								</a>
							</li>
							<li>
								<a href="#two" data-toggle="tab">
									<span>规格与包装</span>
								</a>
							</li>
							<li>
								<a href="#three" data-toggle="tab">
									<span>售后保障</span>
								</a>
							</li>
							<li>
								<a href="#four" data-toggle="tab">
									<span>商品评价</span>
								</a>
							</li>
							<li>
								<a href="#five" data-toggle="tab">
									<span>手机社区</span>
								</a>
							</li>
						</ul>
						<div class="clearfix"></div>
						<div class="tab-content tab-wraped">
							<div id="one" class="tab-pane active">
								<span>${goodsDesc.introduction}</span>
							</div>
							<div id="two" class="tab-pane">
								<p>规格与包装</p>
							</div>
							<div id="three" class="tab-pane">
								<p>售后保障</p>
							</div>
							<div id="four" class="tab-pane">
								<p>商品评价</p>
							</div>
							<div id="five" class="tab-pane">
								<p>手机社区</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--页面底部  开始 -->
	<#include "footer.ftl">

</div>




<script type="text/template" id="tbar-cart-item-template">
	<div class="tbar-cart-item" >
		<div class="jtc-item-promo">
			<em class="promo-tag promo-mz">满赠<i class="arrow"></i></em>
			<div class="promo-text">已购满600元，您可领赠品</div>
		</div>
		<div class="jtc-item-goods">
			<span class="p-img"><a href="#" target="_blank"><img src="{2}" alt="{1}" height="50" width="50" /></a></span>
			<div class="p-name">
				<a href="#">{1}</a>
			</div>
			<div class="p-price"><strong>¥{3}</strong>×{4} </div>
			<a href="#none" class="p-del J-del">删除</a>
		</div>
	</div>
</script>
<script type="text/javascript" src="js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#service").hover(function(){
		$(".service").show();
	},function(){
		$(".service").hide();
	});
	$("#shopcar").hover(function(){
		$("#shopcarlist").show();
	},function(){
		$("#shopcarlist").hide();
	});

})
</script>
<script type="text/javascript" src="js/model/cartModel.js"></script>
<script type="text/javascript" src="js/plugins/jquery.easing/jquery.easing.min.js"></script>
<script type="text/javascript" src="js/plugins/sui/sui.min.js"></script>
<script type="text/javascript" src="js/plugins/jquery.jqzoom/jquery.jqzoom.js"></script>
<script type="text/javascript" src="js/plugins/jquery.jqzoom/zoom.js"></script>
<script type="text/javascript" src="index/index.js"></script>

<!--页面底部  结束 -->
</body>

<script type="text/javascript" src="plugins/vue/vuejs-2.5.16.js"></script>
<script type="text/javascript" src="plugins/vue/axios-0.18.0.js"></script>
<script type="text/javascript" src="plugins/vue/qs.js"></script>

<script>
	Vue.prototype.$axios = axios
	axios.defaults.withCredentials =true;
	new Vue({
		el:"#app",
		data:{
			sku:{}, //当前选择的SKU
			specificationItems:{},//当前选择的规格
			num:1 //商品数量
		},
		methods:{
			loadSku:function(){
				this.sku=skuList[0];
				this.specificationItems= JSON.parse(JSON.stringify(this.sku.spec)) ;
			},

			//判断某规格是否被选中
			isSelected:function(key,value){
				if(this.specificationItems[key]==value){
					return true;
				}else{
					return false;
				}
			},
			//匹配两个对象是否相等
			matchObject:function(map1,map2){
				for(var k in map1){
					if(map1[k]!=map2[k]){
						return false;
					}
				}
				for(var k in map2){
					if(map2[k]!=map1[k]){
						return false;
					}
				}
				return true;
			},
			//用户选择规格
			selectSpecification:function(key,value){
				this.specificationItems[key]=value;
				for(var i=0;i<skuList.length;i++){
					if(this.matchObject( skuList[i].spec ,this.specificationItems)){
						this.sku=skuList[i];
						return ;
					}
				}
				this.sku={id:0,title:'-----',price:0};
			},
			//商品数量加减
			addNum:function(x){
				this.num+=x;
				if(this.num<1){
					this.num=1;
				}
			},
			addToCart:function () {//加入购物车
				axios.get("http://localhost:8083/cart/addGoodsToCartList.do",
						{params:{itemId:this.sku.id,num:this.num}})
						.then(function (response) {
							alert(response);
							if (response.data.success){
								location.href="http://localhost:8083/cart.html";
							} else {
								alert(response.data.message);
							}

						}).catch(function (reason) {
						console.log(reason);
						})
			}
		},
		created: function() {
			this.loadSku();
		},
	});
</script>

</html>