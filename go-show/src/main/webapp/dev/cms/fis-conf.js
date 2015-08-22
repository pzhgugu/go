fis.set('namespace', 'cms');
// 所有的文件产出到 static/ 目录下
fis.match('*', {
    release: '/static/${namespace}/$0',
    domain: '/show'
});

fis.match('**map.json', {
    release: '/map/$0'
});

fis.match('**.jsp', {
    release: false
});

fis.match('**.db', {
    release: false
});


fis.match('*', {
  deploy: fis.plugin('local-deliver', {
    to: '../'
  })
})

// optimize
fis.media('p').match('*.js', {
	optimizer : fis.plugin('uglify-js', {
		mangle : {
			expect : [ 'require', 'define', 'some string' ]
		//不想被压的
		}
	})
}).match('*.css', {
	optimizer : fis.plugin('clean-css', {
		'keepBreaks' : true
	//保持一个规则一个换行
	}),
	// 给匹配到的文件分配属性 `useSprite`
	useSprite: true
}).match('*.{js,css,png,gif,jpg}', {
	useHash: true
});