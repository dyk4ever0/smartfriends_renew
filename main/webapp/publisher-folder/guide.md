# Smartwork 가이드

## 폴더 구조
```
smartwork
 ┣ css
 ┣ fonts
 ┣ html
 ┃ ┣ include // 공통 사용되는 마크업 폴더
 ┃ ┃ ┣ common.html
 ┃ ┃ ┣ footer.html
 ┃ ┃ ┣ guide-lnb.html
 ┃ ┃ ┗ header.html
 ┃ ┣ guide.html
 ┃ ┣ main.html
 ┃ ┗ tools-guide.html
 ┣ images // 이미지 폴더
 ┣ js // 스크립트 폴더
 ┗ scss // scss 폴더
```

## SCSS 커맨드
```command
sass --watch scss:css --style compressed
```

## 기타 가이드
- 지원 브라우저 : IE10~IE11 / 모던 브라우저