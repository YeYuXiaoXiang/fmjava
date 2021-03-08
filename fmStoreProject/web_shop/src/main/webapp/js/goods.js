new Vue({
    el:"#app",
    data:{
        categoryList1:[],//分类1数据列表
        categoryList2:[],//分类2数据列表
        categoryList3:[],//分类3数据列表
        grade:1,     //记录当前级别
        catSeleted1:-1,
        catSeleted2:-1,
        catSeleted3:-1,
        typeId:0,
        brandList:[],
        selBrand:-1,

        curImageObj:{
            color:'',
            url:''
        },
        imageList:[],
        specList:[],
        specSeList:[],
        rowList:[],

        isEnableSpec:1,

        goodsEntity:{
            goods:{},
            goodsDesc:{},
            itemList:[]
        }       //最终保存商品的实体

    },
    methods:{
        getQueryString:function(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
            var r = window.location.search.substr(1).match(reg);
            if (r!=null) return (r[2]); return null;
        },
        loadCateData: function (id) {
            _this = this;
            axios.post("/itemCat/findByParentId.do?parentId="+id)
                .then(function (response) {
                    if (_this.grade == 1){
                        //取服务端响应的结果
                        _this.categoryList1 = response.data;
                    }
                    if (_this.grade == 2){
                        //取服务端响应的结果
                        _this.categoryList2 =response.data;
                    }
                    if (_this.grade == 3){
                        //取服务端响应的结果
                        _this.categoryList3 =response.data;
                    }
                }).catch(function (reason) {
                console.log(reason);
            })
        },
        getCategorySel:function (grade) {
            if (grade == 1){
                this.categoryList2 = [];
                this.catSeleted2 = -1;

                this.categoryList3 = [];
                this.catSeleted3 = -1;

                this.grade = grade +1;
                this.loadCateData(this.catSeleted1);
            }
            if (grade == 2){
                this.categoryList3 = [];
                this.catSeleted3 = -1;

                this.grade = grade +1;
                this.loadCateData(this.catSeleted2);
            }
            if (grade == 3){
                var _this = this;
                axios.post("/itemCat/findOneCategory.do?id="+this.catSeleted3)
                    .then(function (response) {
                    _this.typeId = response.data.typeId;
                    }).catch(function (reason) {
                    console.log(reason);
                })
            }

        },

        uploadFile:function () {
            var _this = this;
            var formData = new FormData();
            formData.append("file",file.files[0]);
            var instance = axios.create({
                withCredentials:true
            });
            axios.post("/upload/uploadFile.do",formData).then(function (response) {
                console.log(response.data);
                _this.curImageObj.url = response.data.message;

            }).catch(function (reason) {
                console.log(reason);
            })
        },
        saveImage:function () {
            if (this.curImageObj.color == '' || this.curImageObj.url == ''){
                alert("请输入颜色或图片");
                return;
            }
            var obj = {color:this.curImageObj.color, url:this.curImageObj.url};
            this.imageList.push(obj);
            this.curImageObj.color = '';
            this.curImageObj.url = '';
        },
        deleteImg:function (url, index) {
            var _this = this;
            axios.get("/upload/deleteImg.do?url="+url).then(function (response) {
                if (response.data.success){

                    alert(response.data.message);
                    _this.imageList.splice(index, 1);

                } else {
                    alert(response.data.message);
                }

            }).catch(function (reason) {
                console.log(reason);
            })
        },
        searchObjectWithKey:function(list,key,value){
            for (var i=0; i <list.length; i++){
                if (list[i][key] == value){
                    return list[i];
                }
            }
            return null;
        },
        updateSpecState:function (event, specName, optionName) {
            var obj = this.searchObjectWithKey(this.specSeList,"specName",specName);
            if (obj != null){
                if (event.target.checked){
                    obj.specOptions.push(optionName);
                } else{
                    var idx = obj.specOptions.indexOf(optionName);
                    obj.specOptions.splice(idx,1);

                    if (obj.specOptions.length == 0){
                        var idx = this.specSeList.indexOf(obj);
                        this.specSeList.splice(idx,1);
                    }
                }
            } else {
                this.specSeList.push({"specName":specName, "specOptions":[optionName]});
            }
            this.createRowList();
        },

        createRowList:function () {
            var rowList = [
                {spec:{},price:0,num:9999,status:'0',isDefault:'0'}
            ];

            for (var i = 0; i<this.specSeList.length;i++){
                var  specObj =  this.specSeList[i];
                var specName =specObj.specName;
                var specOptions = specObj.specOptions;
                var newRowList = [];
                for (var j=0; j < rowList.length; j++){
                    var oldRow = rowList[j];
                    for (var k = 0; k< specOptions.length; k++){
                        var newRow = JSON.parse(JSON.stringify(oldRow));
                        newRow.spec[specName]=specOptions[k];
                        newRowList.push(newRow);
                    }
                }
                rowList = newRowList;
            }

            this.rowList = rowList;

        },
        saveGoods:function () {

            this.goodsEntity.goods.category1Id = this.catSeleted1;
            this.goodsEntity.goods.category2Id = this.catSeleted2;
            this.goodsEntity.goods.category3Id = this.catSeleted3;
            this.goodsEntity.goods.typeTemplateId=this.typeId,
            this.goodsEntity.goods.brandId=this.selBrand,
            this.goodsEntity.goods.isEnableSpec=this.isEnableSpec,

            this.goodsEntity.goodsDesc.itemImages=this.imageList,
            this.goodsEntity.goodsDesc.specificationItems=this.specSeList,
            this.goodsEntity.goodsDesc.introduction=UE.getEditor('editor').getContent(),
            this.goodsEntity.itemList = this.rowList;
            
            if (this.selBrand == -1){
                alert("请选择品牌");
                return ;
            }
            var id = this.getQueryString("id");
            var url = '';
            if (id = null){
                url = '/goods/add.do';

            } else {
                url = '/goods/update.do';
            }

            //发送请求
            axios.post("url",this.goodsEntity)
                .then(function (response) {
                    console.log(response.data);
                    location.href="goods.html";
                }).catch(function (reason) {
                alert(response.data.message);
            });

        },
        checkSpecState:function (specName, specOption) {
            var obj = this.searchObjectWithKey(this.specSeList,"specName",specName);
            if (obj != null){
                if (obj.specOptions.indexOf(specOption) >= 0){
                    return true;
                }else {
                    return false;
                }
            }
        }

    },
    watch:{
        typeId:function (newValue, oldValue) {
           var _this = this;
            _this.brandList =[];
            _this.selBrand = -1;
            axios.post("/temp/findOne.do?id="+newValue)
                .then(function (response) {
                    _this.brandList = JSON.parse(response.data.brandIds);
                    if (_this.goodsEntity.goods.brandId != null){
                        _this.selBrand = _this.goodsEntity.goods.brandId;
                    }
                }).catch(function (reason) {
                console.log(reason);
            });

            _this.specList = [];
            axios.post("/temp/findBySpecWithID.do?id="+newValue).then(function (response) {
                _this.specList = response.data;
            }).catch(function (reason) {
                console.log(reason);

            })



        }
    },
    
    created: function() {
        this.loadCateData(0);
    },
    mounted:function () {
        var id = this.getQueryString("id");
        var _this = this;
        if (id != null){
            //根据id查询当前商品
            axios.get("/goods/findOne.do?id="+id)
                .then(function (response) {
                    console.log(response.data);

                    _this.goodsEntity.goods = response.data.goods;
                    _this.goodsEntity.goodsDesc = response.data.goodsDesc;

                    _this.typeId = response.data.goods.typeTemplateId;
                    _this.imageList = JSON.parse(response.data.goodsDesc.itemImages);

                    UE.getEditor("editor").ready(function () {
                        UE.getEditor("editor").setContent(response.data.goodsDesc.introduction);
                    });
                    //选中规格
                    _this.specSelList  = JSON.parse(response.data.goodsDesc.specificationItems);
                    //库存列表
                    _this.rowList = response.data.itemList;
                    for (var i = 0; i< _this.rowList.length; i++){
                        _this.rowList[i].spec =  JSON.parse(_this.rowList[i].spec);
                    }

                    _this.catSeleted1 = response.data.goods.category1Id;
                    if (_this.goodsEntity.goods.category1Id > 0){
                        _this.grade = 2;
                        _this.loadCateData(_this.catSeleted1);
                        _this.catSeleted2 = response.data.goods.category2Id;
                    }
                    if (_this.goodsEntity.goods.category2Id > 0){
                        axios.post("/itemCat/findByParentId.do?parentId="+_this.goodsEntity.goods.category2Id)
                            .then(function (response) {
                                _this.categoryList3 =response.data;

                            }).catch(function (reason) {
                            console.log(reason);
                        });
                        _this.catSeleted3 = response.data.goods.category3Id;
                    }

                }).catch(function (reason) {
                console.log(reason);
            });

        }
    }

});
