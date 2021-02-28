"打开行号
set number
"打开语法高亮
syntax on
"打开相对行号
set relativenumber
"设置剪切板
set clipboard=unamed
"支持使用鼠标
"set mouse=a
"使用编码的格式
set encoding=utf-8
"打开自动缩进
set autoindent
"设置缩进的空格长度
set shiftwidth=2
"设置tab显示的空格
set softtabstop=2
set ts=2
"设置光标所在行高亮
"set cursorline
"光标遇到括号,自动高亮对应的括号
"set showmatch
"搜索时高亮搜索到的结果
set hlsearch
"设置搜索时自动的跳转
set incsearch
"搜索忽略大小写
set ignorecase
"设置右侧分屏
set splitright
"设置状态栏
set laststatus=2
set statusline=%t\ %y\ format:\ %{&ff};\[%c,%l]

set smartcase
"设置public等缩进
set cino=g0
"设置折叠的方式
set foldmethod=indent
"设置折叠的层数
set foldlevel=1
set foldlevelstart=99
"打开文件到上次浏览的位置
if has("autocmd")
  au BufReadPost * if line("'\"") > 1 && line("'\"") <= line("$") | exe "normal! g'\"" | endif
endif 

call plug#begin('~/.vim/autoload/plugged')
Plug 'ycm-core/youcompleteme'
Plug 'raimondi/delimitmate'
Plug 'scrooloose/nerdtree'
Plug 'davidhalter/jedi-vim'
Plug 'gcmt/wildfire.vim'
Plug 'tpope/vim-surround'
"Plug 'dense-analysis/ale'
Plug 'vim-scripts/DoxygenToolkit.vim'
"Plug 'mbbill/echofunc'

call plug#end()

"设置youcompleteme
let g:ycm_global_ycm_extra_conf = '/home/raymond-du/.vim/autoload/plugged/youcompleteme/.ycm_extra_conf.py'

" 输入第一个字符就开始补全
let g:ycm_min_num_of_chars_for_completion=2

let g:ycm_semantic_triggers =  {
			\ 'c,cpp,python,java,go,erlang,perl': ['re!\w{2}'],
			\ }
set completeopt=menu,menuone
let g:ycm_add_preview_to_completeopt = 0

let g:ycm_filetype_whitelist = {
			\ "c":1,
			\ "cpp":1,
			\ "h":1,
			\ "py":1,
			\}
"设置语义检测关闭
let g:ycm_show_diagnostics_ui = 1
let g:ycm_enable_diagnostic_highlighting = 0
nnoremap gc :YcmCompleter GoToDeclaration<CR>
" 只能是 #include 或已打开的文件
nnoremap gf :YcmCompleter GoToDefinition<CR>

map <C-b> :NERDTreeToggle<CR>
let NERDTreeWinSize=25

set tags+=/usr/include/tags
set tags+=/usr/local/include/tags

"-----------------------------------------------------------------------------
" plugin - ale.vim
"-----------------------------------------------------------------------------
"keep the sign gutter open
"let g:ale_sign_column_always = 1
"let g:ale_sign_error = '>>'
"let g:ale_sign_warning = '--'
"
"" show errors or warnings in my statusline
"let g:airline#extensions#ale#enabled = 1
"" self-define statusline
""function! LinterStatus() abort
""    let l:counts = ale#statusline#Count(bufnr(''))
""
""    let l:all_errors = l:counts.error + l:counts.style_error
""    let l:all_non_errors = l:counts.total - l:all_errors
""
""    return l:counts.total == 0 ? 'OK' : printf(
""    \  '%dW %dE',
""    \  all_non_errors,
""    \  all_errors
""    \)
""endfunction
""set statusline=%{LinterStatus()}
"" echo message
"" %s is the error message itself
"" %linter% is the linter name
"" %severity is the severity type
"" let g:ale_echo_msg_error_str = 'E'
"" let g:ale_echo_msg_warning_str = 'W'
"" let g:ale_echo_msg_format = '[%linter%] %s [%severity%]'
"
"" use quickfix list instead of the loclist
"let g:ale_set_loclist = 0
"let g:ale_set_quickfix = 1
"" only enable these linters
""let g:ale_linters = {
""\    'javascript': ['eslint']
""\}
"nmap <silent> <C-k> <Plug>(ale_previous_wrap)
"nmap <silent> <C-J> <Plug>(ale_next_wrap)
"" run lint only on saving a file
"" let g:ale_lint_on_text_changed = 'never'
"" dont run lint on opening a file
"" let g:ale_lint_on_enter = 0
"let g:ale_set_highlights = 0
"let g:ale_enabled = 0


let g:DoxygenToolkit_briefTag_funcName = "yes"

" for C++ style, change the '@' to '\'
"let g:DoxygenToolkit_commentType = "C++"
let g:DoxygenToolkit_briefTag_pre = "@brief "
let g:DoxygenToolkit_templateParamTag_pre = "@tparam "
let g:DoxygenToolkit_paramTag_pre = "@param "
let g:DoxygenToolkit_returnTag = "@return "
let g:DoxygenToolkit_throwTag_pre = "@throw "
let g:DoxygenToolkit_classTag = "@class "

"c++注释的操作
map <C-n> :Dox<CR>
inoremap <C-n> <ESC>:Dox<CR>

let g:jedi#completions_command = "<Tab>"
"设置 在可视模式和普通模式下可以进行 tab 缩进
map <tab> V>
map <s-tab> V<

map <F5> :call CompileRunCode()<CR>

func! CompileRunCode()
	exec "wa"
	if &filetype == 'python'
		exec '!python3 %'
	elseif &filetype == 'cpp' || &filetype == 'h'
		exec '!make'
	endif
endfunc

func! FileSwitch()
	let file_path = expand("%")
	let file_name = expand("%<")
	let extension = expand("%:e")
	let err_msg = "There is no file"

	if extension == "cpp" || extension == "c"
		let next_file = join([file_name,".h"],"")
			
		if filereadable(next_file)
			:w 
			:e %<.h
		else
			echo join([err_msg,next_file],"")
		endif
	elseif extension == "h"
		let next_cfile = join([file_name,"c"],"")
		let next_cppfile = join([file_name,"cpp"],"")
		
		if filereadable(next_cfile)
			:w
			:e %<.c
		else
			:w
			:e %<.cpp
		endif
	endif
endfunc

command! -nargs=0 A call FileSwitch()

map <Left> <Nop>
map <right> <Nop>
map <up> <NOp>
map <down> <nop>
