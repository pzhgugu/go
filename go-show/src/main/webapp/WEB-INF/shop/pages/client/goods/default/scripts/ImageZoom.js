/**
* ImgZoom.js
* 基于jQuery扩展插件 ImgZoom
* create: 2012.02.03
* update: 2013.05.23
* admin@laoshu133.com

-- 参数说明 --
	@options {Object}
		- {
			tmpl: '<div class="gallery_wrap">{gallery}</div><div class="controller_wrap"><div class="controller">{controllers}</div><a href="javascript:;" class="prev"><span>&#171;</span></a><a href="javascript:;" class="next"><span>&#187;</span></a></div><div class="close_wrap">{closeButton}</div>',
			items: [],
			shell: null,
			basePath: 'images/',
			levelASize: [60, 60],
			levelBSize: [240, 240],
			levelCSize: [450, 450],
			levelDSize: [1200, 1200],
			gallerySize: [600, 400],
			effectDuration: 400,
			limitDrag: true,
			drag: true,
			esc: true,
			onZoom: noop,
			onBeforeZoom: noop
		}
-- 参数说明 end --
*/
(function(global, document, $, undefined){
	var 
	noop = $.noop,
	hasCapture = !!document.documentElement.setCapture,
	levelHash = {A:0, B:1, C:2, D:3}, levelMap = ['A', 'B', 'C', 'D'],
	galleryTmpl = '<div class="gallery"><img src="{src}" alt="" /></div>',
	ImageZoom = global.ImageZoom = function(ops){
		this.init(ops || {});
	};
	ImageZoom.prototype = {
		constructor: ImageZoom,
		init: function(ops){
			$.each(ImageZoom.defaultOptions, function(k, val){
				if(ops[k] === void 0){
					ops[k] = val;
				}
			});
			this.shell = $(ops.shell);
			this.index = -1;
			this.level = 0;
			this.ops = ops;
			if(ops.items.length > 0 && this.shell.length > 0){
				this.buildDOM();
				this.initEvent();
				this.zoomTo(0, 1);
			}
		},
		buildDOM: function(){
			var ops = this.ops, shell = this.shell;
			this.placeHTML = shell.html();

			var 
			html = ops.tmpl,
			controllerHTML = '<ul>',
			galleryHTML = galleryTmpl.replace('{src}', ops.basePath + ops.items[0].levelB);
			html = html.replace('{gallery}', galleryHTML);
			$.each(ops.items, function(i){
				controllerHTML += ['<li><a href="javascript:;" data-index="', i, '"><img src="', 
					ops.basePath + this.levelA, '" height="', ops.levelASize[0], '" width="', 
					ops.levelASize[0], '" alt="" /></a></li>'].join('');
			});
			controllerHTML += '</ul>';
			html = html.replace('{controllers}', controllerHTML);
			html = html.replace('{closeButton}', '<a href="javascript:;" class="close">×</a>');
			shell.html(html).addClass('image_zoom');

			var gallery = this.gallery = shell.find('.gallery').eq(0);
			this.galleryPanel = gallery.parent().css({
				height: ops.gallerySize[1],
				width: ops.gallerySize[0],
				position: 'relative',
				overflow: 'hidden'
			});
			gallery.css('position', 'absolute').css('overflow', 'hidden').css('opacity', 0);
			this.fakeGallery = gallery.clone().hide().addClass('gallery_mask');
			this.controllers = shell.find('.controller a');
			this.fakeImage = this.fakeGallery.find('img');
			this.image = gallery.find('img');
			this.galleryPanel.append(this.fakeGallery);
		},
		initEvent: function(){
			var timer, self = this, zoomTo = function(){
				clearTimeout(timer);
				self.zoomTo.apply(self, arguments);
			};
			this.shell.delegate('.controller a', 'mouseenter', function(){
				if(self.level < 3){
					var trigger = this;
					clearTimeout(timer);
					timer = setTimeout(function(){
						zoomTo(~~trigger.getAttribute('data-index'), self.level);
					}, 240);
				}
			})
			.delegate('.controller a', 'mouseleave', function(){
				clearTimeout(timer);
			})
			.delegate('.controller a', 'click', function(e){
				e.preventDefault();
				zoomTo(~~this.getAttribute('data-index'), 2);
			})
			.delegate('.close', 'click', function(e){
				e.preventDefault();
				zoomTo(self.index, 1);
			})
			.delegate('a.prev,a.next', 'click', function(e){
				e.preventDefault();
				var index = this.className.indexOf('prev')>-1 ? self.index-1 : self.index+1;
				zoomTo(index, Math.min(2, self.level));
			});

			this.gallery.bind('click', function(e){
				e.preventDefault();
				zoomTo(self.index, self.level < 3 ? self.level+1 : 2, [e.pageX, e.pageY]);
			});
			this.fakeGallery.bind('mousedown click', function(e){
				return false;
			});
			
			if(this.ops.esc){
				$(document).bind('keydown.image_zoom', function(e){
					if(!self.zooming && self.level > 1 && e.keyCode === 27){
						zoomTo(self.index, self.level-1);
					}
				});
			}
		},
		zoomTo: function(index, level, pointXY){
			var 
			self = this,
			ops = this.ops,
			prevIndex = this.index, prevLevel = this.level;
			level = Math.max(1, levelHash[String(level).toUpperCase()] || ~~level);
			index = ~~index % ops.items.length;
			index = index < 0 ? ops.items.length + index : index;

			if(!this.draging && 
				(index !== prevIndex || level !== prevLevel) && 
				ops.onBeforeZoom.call(this, index, level, prevIndex, prevLevel) !== false
			){
				//console.log(levelMap[level], index, level, prevIndex, prevLevel);
			
				var 
				levelName = levelMap[level],
				size = ops['level' + levelName + 'Size'],
				image = this.image, gallery = this.gallery,
				fakeImage = this.fakeImage, fakeGallery = this.fakeGallery,
				galleryCSSProps = {
					height: size[1],
					width: size[0],
					opacity: 1
				},
				imageCSSProps = {
					height: size[1],
					width: size[0]
				},
				zoomGallery = function(){
					var imageUrl = ops.basePath + ops.items[index]['level' + levelName];
					loadImage(imageUrl, function(){
						fakeImage.attr('src', imageUrl).css(imageCSSProps);
						fakeGallery.stop().css(galleryCSSProps).css('opacity', 0).show();
						fakeGallery.animate({opacity:1}, ops.effectDuration, function(){
							gallery[0].className = 'gallery level' + levelName;
							image.attr('src', imageUrl);
							fakeGallery.hide();
						});

						if(index !== prevIndex){
							self.controllers.eq(prevIndex).removeClass('current');
							self.controllers.eq(index).addClass('current');
							self.index = index;
						}
						self[ops.drag && level >= 3 ? 'initDrag' : 'releaseDrag']();
						self.zooming = false;
						self.level = level;
						ops.onZoom.call(self, index, level, prevIndex, prevLevel);
					});
				};
				
				if(level >= 3){
					var 
					galleryOffset = gallery.offset(),
					panelOffset = this.galleryPanel.offset(),
					prevSize = ops['level' + levelMap[prevLevel] + 'Size'],
					position = [galleryOffset.left - panelOffset.left, galleryOffset.top - panelOffset.top];
					if(!pointXY || pointXY.length !== 2){
						pointXY = [galleryOffset.left + prevSize[0]/2, galleryOffset.top + prevSize[1]/2];
					}
					
					var 
					offset = [pointXY[0] - galleryOffset.left, pointXY[1] - galleryOffset.top],
					ratio = [offset[0]/prevSize[0], offset[1]/prevSize[1]],
					xy = this.getLimitXY(position[0] - size[0]*ratio[0] + offset[0], 
						position[1] - size[1]*ratio[1] + offset[1]);
					galleryCSSProps.left = xy[0];
					galleryCSSProps.top = xy[1];
				}
				else{
					galleryCSSProps.left = (ops.gallerySize[0] - size[0]) / 2;
					galleryCSSProps.top = (ops.gallerySize[1] - size[1]) / 2;
				}
				this.zooming = true;
				if(level !== prevLevel){
					fakeGallery.stop().hide();
					image.stop().animate(imageCSSProps, ops.effectDuration);
					gallery.stop().animate(galleryCSSProps, ops.effectDuration, zoomGallery);
				}
				else{
					zoomGallery();
				}
			}
			return this;
		},
		getLimitXY: function(x, y, level){
			var ops = this.ops, size = ops['level' + (levelMap[level] || 'D') + 'Size'];
			return [Math.min(0, Math.max(ops.gallerySize[0] - size[0], ~~x)), 
				Math.min(0, Math.max(ops.gallerySize[1] - size[1], ~~y))];
		},
		initDrag: function(){
			var self = this, ops = this.ops;
			if(!ops.limitDrag){
				this.getLimitXY = function(){
					return arguments;
				};
			}
			var 
			offset = [0, 0],
			doc = $(document),
			gallery = this.gallery,
			galleryStyle = gallery[0].style,
			moveHandler = function(e){
				var 
				panelOffset = self.galleryPanel.offset(),
				xy = self.getLimitXY(e.pageX - panelOffset.left - offset[0],
					e.pageY - panelOffset.top - offset[1]);
				galleryStyle.left = xy[0] + 'px';
				galleryStyle.top = xy[1] + 'px';
				self.draging = true;
			};
			gallery.bind('mousedown.image_zoom', function(e){
				e.preventDefault();
				var galleryOffset = gallery.offset();
				offset[0] = e.pageX - galleryOffset.left;
				offset[1] = e.pageY - galleryOffset.top;
				hasCapture && gallery[0].setCapture();
				
				doc.bind('mousemove', moveHandler)
				.one('mouseup', function(){
					hasCapture && gallery[0].releaseCapture();
					doc.unbind('mousemove', moveHandler);
				});
				gallery.one('click', function(){
					self.draging = false;
				});
				self.releaseDrag = function(){
					this.gallery.unbind('mousedown.image_zoom');
					delete this.releaseDrag;
				};
			});
		},
		releaseDrag: noop,
		prev: function(){
			return this.zoomTo(this.index-1, this.level);
		},
		next: function(){
			return this.zoomTo(this.index+1, this.level);
		},
		destroy: function(){
			this.shell.html(this.placeHTML);
			this.shell = null;
		}
	};
	ImageZoom.defaultOptions = {
		tmpl: '<div class="gallery_wrap">{gallery}</div><div class="controller_wrap"><div class="controller">{controllers}</div><a href="javascript:;" class="prev"><span>&#171;</span></a><a href="javascript:;" class="next"><span>&#187;</span></a></div><div class="close_wrap">{closeButton}</div>',
		items: [],
		shell: null,
		basePath: 'images/',
		levelASize: [60, 60],
		levelBSize: [240, 240],
		levelCSize: [450, 450],
		levelDSize: [1200, 1200],
		gallerySize: [600, 400],
		effectDuration: 400,
		limitDrag: true,
		drag: true,
		esc: true,
		onZoom: noop,
		onBeforeZoom: noop
	};
	String('B,C,D').replace(/\w+/g, function(k){
		ImageZoom.prototype['zoomTo' + k] = function(){
			this.zoomTo(this.index, levelHash[k]);
		};
	});
	//Extend Funs
	var 
	imgCache = {},
	loadImage = function(url, callback){
		var cache = imgCache[url];
		if(!cache){
			cache = imgCache[url] = {callbackQueue:[]};
		}
		if(cache.loaded){
			(callback || noop).call(null, url);
		}
		else if(typeof callback === 'function'){
			cache.callbackQueue.push(callback);

			var img = new Image();
			img.onload = img.onerror = function(){
				img.onload = img.onerror = null;
				for(var i=0,len=cache.callbackQueue.length; i<len; i++){
					cache.callbackQueue[i].call(null, url);
				}
				cache.callbackQueue = [];
				cache.loaded = true;
			};
			img.src = url;
		}
	};
})(window, document, jQuery);