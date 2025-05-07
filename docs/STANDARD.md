# Hướng dẫn làm việc nhóm với Git và GitHub

## Giới thiệu GitHub và làm việc nhóm qua Git

Git là một hệ thống quản lý phiên bản phân tán cho phép nhiều người cùng làm việc trên mã nguồn một cách an toàn và hiệu quả. GitHub là một dịch vụ lưu trữ mã nguồn Git trực tuyến, cung cấp giao diện và công cụ hỗ trợ làm việc nhóm. Sử dụng GitHub trong dự án giúp nhóm theo dõi lịch sử thay đổi của mã, chia nhỏ công việc, và tích hợp đóng góp của từng thành viên dễ dàng.

Trong dự án môn học (Java Swing + MySQL) của nhóm 5 sinh viên năm cuối này, việc sử dụng GitHub là rất quan trọng. Hai thành viên đã có kinh nghiệm dùng Git cá nhân, trong khi ba thành viên còn lại hầu như chưa quen. Vì vậy, tài liệu này sẽ hướng dẫn một cách chi tiết và từng bước cách làm việc nhóm với Git/GitHub – từ mô hình nhánh (branch) phù hợp đến cách commit, tạo Pull Request, review code, quản lý issue, cũng như các thói quen tốt giúp dự án diễn ra trơn tru. Mục tiêu là mọi thành viên đều hiểu rõ quy trình làm việc, tuân thủ các quy ước chuyên nghiệp, và phối hợp hiệu quả trên GitHub.

## Quy trình Git Flow cho nhóm dự án

Git Flow là một mô hình làm việc với Git phân chia công việc theo các loại nhánh, giúp quản lý quá trình phát triển và phát hành phần mềm có tổ chức. Mô hình này được giới thiệu bởi Vincent Driessen (nvie) và được nhiều nhóm dự án áp dụng. Git Flow của nhóm dự án lần này có 4 nhánh chính:

- **Main (hoặc Master)** – nhánh chính chứa mã nguồn production ổn định (phiên bản có thể phát hành). Nhánh main được tạo khi khởi tạo repo và luôn ở trạng thái sẵn sàng phát hành. Không ai được commit trực tiếp vào main trừ khi đã qua quy trình kiểm tra (Pull Request).

- **Develop** – nhánh phát triển, nhánh mặc định cho môi trường phát triển (pre-production). Mã nguồn trên develop bao gồm các tính năng mới đang được phát triển và tích hợp, chưa phải bản phát hành chính thức. Đây là nhánh mà nhóm sẽ hợp nhất (merge) tất cả các nhánh tính năng sau khi hoàn thành để chuẩn bị cho bản phát hành tiếp theo.

- **Feature** – các nhánh tính năng, được tạo ra từ develop để phát triển một tính năng hoặc nhiệm vụ mới. Mỗi tính năng lớn nên có một nhánh riêng. Khi hoàn thành, nhánh feature sẽ được merge trở lại develop (thông qua Pull Request).

- **Hotfix** – các nhánh sửa lỗi nóng, tạo trực tiếp từ main khi cần sửa nhanh các lỗi nghiêm trọng ở môi trường production. Sau khi hoàn tất, nhánh hotfix sẽ được merge vào cả main (để phát hành bản vá) và develop (để đảm bảo sửa lỗi cũng có trong mã nguồn phát triển).

Sơ đồ mô hình nhánh Git Flow minh họa cách các nhánh Feature, Hotfix tương tác với nhánh Develop và Main. Quy trình Git Flow thực tế sẽ diễn ra như sau:

1. **Bắt đầu dự án**: Tạo repository Git trên GitHub với nhánh mặc định main. Sau đó tạo thêm nhánh develop từ main. Thiết lập develop làm nhánh mặc định để mọi người làm việc trên đó.

2. **Phát triển tính năng**: Mỗi khi bắt đầu một tính năng hoặc nhiệm vụ, tạo một nhánh feature từ develop. Ví dụ: `git checkout -b feature/login-screen develop` (phát triển chức năng màn hình đăng nhập). Toàn bộ công việc code cho tính năng đó sẽ diễn ra trên nhánh feature này.

3. **Liên tục tích hợp**: Trong quá trình làm, thường xuyên commit và push nhánh feature lên GitHub (xem hướng dẫn chi tiết ở phần sau). Điều này giúp lưu trữ lịch sử, chia sẻ code cho nhóm và giảm thiểu xung đột.

4. **Hoàn thành tính năng**: Khi tính năng đã code xong và được thử nghiệm cục bộ, mở một Pull Request (PR) để yêu cầu merge nhánh feature vào nhánh develop. Các thành viên khác sẽ review (xem xét) code. Sau khi được chấp nhận, nhánh feature được merge vào develop (thông qua PR) và có thể xoá nếu không cần thiết nữa.

5. **Chuẩn bị phát hành**: Sau khi nhiều tính năng trên develop đã hoàn thành và sẵn sàng cho một đợt giao bài. Khi mọi thứ ổn định, nhóm trưởng hoặc người chịu trách nhiệm sẽ merge nhánh develop vào main (tạo bản phát hành chính thức) đồng thời gắn tag phiên bản (ví dụ: v1.0).

6. **Sửa lỗi khẩn cấp (nếu cần)**: Nếu xuất hiện lỗi nghiêm trọng trên bản phát hành (nhánh main), tạo nhanh một nhánh hotfix từ main (ví dụ: hotfix/fix-null-pointer). Sửa lỗi trên nhánh này, sau đó merge ngay vào main để cập nhật phiên bản production và cũng merge vào develop (hoặc release đang có nếu giai đoạn đó đang ở giữa một chu kỳ phát hành).

**Lưu ý**: Hai nhánh chính là main (chứa bài nộp chính thức) và develop (chứa mã đang phát triển). Mọi công việc nên diễn ra trên các nhánh tính năng tách biệt, sau đó hợp nhất vào develop khi xong. Như vậy, main luôn sạch sẽ và sẵn sàng khi cần chốt phiên bản demo/báo cáo.

## Đặt tên nhánh (branch) theo chuẩn chuyên nghiệp

Đặt tên nhánh một cách nhất quán và dễ hiểu sẽ giúp nhóm nhanh chóng biết nhánh đó dùng để làm gì. Dưới đây là một số quy tắc và gợi ý đặt tên branch:

### Viết thường, dùng dấu gạch ngang (-) thay cho khoảng trắng
Tên branch nên dùng toàn chữ thường và dấu - để phân tách các từ. Tránh dùng khoảng trắng, gạch dưới _ hoặc ký tự đặc biệt. Ví dụ: `feature/login-page` hoặc `bugfix/header-styling` là tên branch rõ ràng, ngắn gọn.

### Ngắn gọn, mô tả ý nghĩa
Tên branch nên phản ánh nội dung công việc một cách súc tích. Tránh đặt tên quá dài. Không nên chỉ đặt một chuỗi số hoặc ký tự vô nghĩa. Ví dụ, thay vì đặt `branch123` nên đặt `feature/user-auth` để diễn tả nhánh dùng phát triển chức năng "xác thực người dùng".

### Sử dụng tiền tố (prefix) theo loại công việc cho tên branch
Một thông lệ tốt là bắt đầu tên nhánh bằng một tiền tố chung để phân loại nhánh (branch). Một số tiền tố thường dùng và ý nghĩa:

- **feature/** – dành cho phát triển tính năng mới. Ví dụ: `feature/payment-integration` (tích hợp thanh toán).
- **bugfix/** hoặc **fix/** – dành cho sửa lỗi. Ví dụ: `bugfix/login-validation` (sửa lỗi kiểm tra đăng nhập).
- **hotfix/** – dành cho sửa lỗi khẩn cấp trên môi trường production (theo mô hình Git Flow). Ví dụ: `hotfix/security-patch`.
- **release/** – dành cho chuẩn bị bản phát hành. Ví dụ: `release/v1.0.0` (nhánh chuẩn bị phát hành phiên bản 1.0.0).
- **docs/** – dành cho cập nhật tài liệu. Ví dụ: `docs/update-readme`.
- **chore/** – dành cho các nhiệm vụ vặt không thuộc mã nguồn ứng dụng (như cấu hình build, cải thiện cấu trúc thư mục, v.v.).

Sử dụng các tiền tố này giúp mọi người nhìn vào tên branch biết ngay mục đích của nó. Ví dụ, nhánh tên `feature/add-login` rõ ràng là để thêm chức năng đăng nhập, còn `fix/ui-bug-homepage` là để sửa một lỗi giao diện trên trang chủ.

**Cả nhóm đều phải thống nhất một chuẩn đặt tên branch như quy tắc trên.**

### Kết hợp mã issue
Dự án có quản lý nhiệm vụ bằng Issue (GitHub), thêm mã số này vào tên branch để dễ đối chiếu. Ví dụ: nếu issue #15 trên GitHub là "Thêm chức năng tìm kiếm", có thể đặt tên branch là `feature/15-add-search` hoặc `feature/issue-15-add-search`. Cách này giúp liên kết branch với nhiệm vụ cụ thể, dễ tra cứu sau này.

**Bắt buộc phải làm để dễ quản lý.**

### Ví dụ tên branch tốt
Dựa trên các quy tắc trên, dưới đây là một vài ví dụ:

- `feature/user-authentication` – phát triển tính năng đăng nhập/xác thực người dùng.
- `fix/null-pointer-profile` – sửa lỗi trỏ null trong chức năng hồ sơ.
- `docs/update-install-guide` – cập nhật hướng dẫn cài đặt.
- `hotfix/login-crash` – nhánh hotfix xử lý lỗi sập khi đăng nhập.

Cả nhóm đều phải thống nhất một chuẩn đặt tên branch như ví dụ trên.

## Quy tắc commit và viết thông điệp (commit message) rõ ràng

Commit thường xuyên với thông điệp rõ ràng giúp theo dõi sự thay đổi của dự án và hiểu được tại sao code thay đổi. Phải tuân theo chuẩn Conventional Commits để viết thông điệp commit nhất quán và có ý nghĩa. Một số hướng dẫn chính:

### Cú pháp chung
Thông điệp commit gồm một dòng tiêu đề ngắn gọn mô tả thay đổi, thường kèm một tiền tố loại thay đổi. Cú pháp phổ biến theo Conventional Commits là:

```
<type>(optional scope): <short description>

[optional body]

[optional footer]
```

Trong đó:

- **\<type\>** – là loại thay đổi, ví dụ: feat (tính năng mới), fix (sửa lỗi), docs (tài liệu), style (định dạng, format code), refactor (tái cấu trúc code không làm thay đổi chức năng), test (bổ sung/chỉnh sửa test), chore (công việc lặt vặt, ví dụ chỉnh CI hoặc build). Sử dụng loại commit giúp người đọc log biết ngay mục đích của commit (thêm tính năng hay sửa lỗi?).
  
- **(optional scope)** – phạm vi ảnh hưởng (không bắt buộc) viết trong dấu ngoặc đơn, ví dụ module hoặc file ảnh hưởng. Ví dụ: `feat(UI): ...` để chỉ tính năng thuộc phần UI.

- **: \<short description\>** – mô tả ngắn gọn (tiêu đề) cho thay đổi. Nên giới hạn khoảng 50 ký tự hoặc ít hơn, viết hoa chữ cái đầu câu và không kết thúc bằng dấu chấm. Tiêu đề này nên ở thì hiện tại, mệnh lệnh (imperative) – tức là mô tả làm gì thay vì đã làm gì. Ví dụ, dùng "Add login validation" (Thêm logic kiểm tra đăng nhập) thay vì "Added login validation". Tương tự trong tiếng Việt, nên viết "Thêm kiểm tra đăng nhập" thay vì "Đã thêm...".

- **Phần thân (body)** – tuỳ chọn, dùng khi cần mô tả chi tiết hơn về thay đổi, lý do, hoặc hướng dẫn triển khai. Phần thân nên tách biệt với tiêu đề bằng một dòng trống, có thể gồm nhiều dòng giải thích hoặc liệt kê bullet. Mỗi dòng nên dưới ~72 ký tự để dễ đọc.

- **Footer** – tuỳ chọn, thường dùng để thêm thông tin bổ sung như số issue liên quan, người review, hoặc thông báo breaking change. Theo Conventional Commits, footer thường chứa dòng như `Closes #<issue>` để tự động đóng issue liên quan, hoặc `BREAKING CHANGE: ...` nếu commit này gây thay đổi phá vỡ tương thích.

### Ngôn ngữ
Bắt buộc phải viết commit và đặt tên nhánh (branch) bằng tiếng Anh.

### Ví dụ commit message tốt:

```
feat: add login validation for user input

- Check for empty username/password fields
- Show error messages on invalid input
- Unit tests for login validator

Closes #12
```

Dòng đầu tiên sử dụng tiền tố feat cho biết commit này thêm một tính năng mới, kèm mô tả ngắn gọn "add login validation for user input". Phần thân mô tả chi tiết các thay đổi chính bằng các gạch đầu dòng. Cuối cùng, `Closes #12` cho biết commit này sẽ đóng issue số 12 trên GitHub (xem phần quản lý issue bên dưới).

### Không nên viết các commit mơ hồ
Tránh những thông điệp quá chung chung như "update", "fix bug", "test", ... Những message này không cho biết nội dung gì cụ thể và gây khó khăn khi xem lại lịch sử. Mỗi commit nên tương ứng với một thay đổi có ý nghĩa, nên hãy mô tả nó cụ thể (ví dụ: "fix: sửa lỗi crash khi đăng nhập sai" thay vì chỉ "fix bug").

### Commit nhỏ, theo chức năng
Mỗi commit nên tập trung vào một chức năng hoặc sửa một lỗi cụ thể. Tránh dồn quá nhiều thay đổi không liên quan vào một commit. Commit nhỏ giúp dễ tìm ra nguyên nhân bug (nhờ git bisect), dễ revert (hoàn tác) nếu cần, và dễ code review hơn. Hãy commit thường xuyên sau mỗi phần công việc hoàn thành thay vì để nhiều ngày mới commit một lần. Một hướng dẫn thường được khuyên là commit ít nhất vài lần mỗi ngày khi đang code, hoặc cứ mỗi 1-2 giờ làm việc nên commit một lần. Tất nhiên, không commit những code đang dang dở làm hỏng build của nhánh chính, nhưng cũng không chờ "hoàn hảo" mới commit – commit sớm giúp lưu lại dấu mốc và tránh mất mát công sức nếu có sự cố.

### Đảm bảo code chạy được
Trước khi commit, cố gắng đảm bảo code ít nhất biên dịch được và qua các test cơ bản. Tránh commit những code đang lỗi nghiêm trọng (broken code) lên repository chung, đặc biệt là trên các nhánh chính như develop hoặc main. Nếu commit ở nhánh feature cá nhân chưa hoàn thiện, có thể chú thích bằng tiền tố WIP: (Work In Progress) hoặc tạo nhánh wip/ để mọi người biết đó chưa phải tính năng hoàn chỉnh.

Tóm lại, commit message rõ ràng theo chuẩn Conventional Commits sẽ giúp lịch sử dự án trở nên có ý nghĩa. Khi xem lại log hoặc diff, nhóm sẽ hiểu ngay mỗi commit nhằm mục đích gì, thuộc loại thay đổi nào, có liên kết issue nào không. Điều này đặc biệt hữu ích khi nhiều người cùng đóng góp và khi tổng hợp lại báo cáo cuối kỳ.

## Hướng dẫn quy trình làm việc cơ bản với GitHub (clone, branch, commit, push, PR, review, merge)

Phần này sẽ hướng dẫn các bước thao tác cụ thể với Git và GitHub mà nhóm sẽ sử dụng hàng ngày.

### 1. Clone repository về máy

Thành viên lần đầu tham gia dự án cần clone repo từ GitHub về máy cá nhân. Sử dụng lệnh:

```bash
git clone <repo-url>
```

Trong đó `<repo-url>` là đường link Git (HTTPS hoặc SSH) của repository trên GitHub (nút "Clone" trên GitHub cung cấp URL này). Lệnh này sẽ tải toàn bộ mã nguồn và lịch sử commit về thư mục máy bạn. Sau khi clone, hãy cd vào thư mục dự án.

Thiết lập ban đầu: Nếu repo có sẵn các nhánh như main và develop, bạn có thể chạy:

```bash
git pull origin develop
git checkout develop
```

để đảm bảo đang ở nhánh develop mới nhất (nhánh mà nhóm sẽ phát triển).

### 2. Đồng bộ mã (Pull thường xuyên)

Trong quá trình làm việc, trước khi bắt đầu một nhiệm vụ mới hoặc định push code, hãy pull những thay đổi mới nhất từ GitHub về để đồng bộ với nhóm:

```bash
git pull origin develop
```

Lệnh này lấy cập nhật từ nhánh develop trên GitHub về và hợp nhất vào nhánh hiện tại (thường là develop local của bạn). Làm như vậy thường xuyên (ví dụ mỗi ngày hoặc mỗi khi biết có người khác vừa merge code) sẽ giúp bạn tránh bị xung đột (conflict) do code của người khác và code của bạn chỉnh sửa cùng chỗ.

**Mẹo**: Thiết lập kênh trao đổi (Messenger/Zalo/Slack...) để thông báo mỗi khi ai đó vừa merge code mới, để mọi người cùng pull sớm.

### 3. Tạo nhánh mới cho công việc

Khi bắt đầu làm một tính năng hoặc sửa một lỗi, tuyệt đối không làm trực tiếp trên nhánh develop hoặc main. Thay vào đó, tạo một nhánh mới từ develop:

```bash
git checkout -b feature/<ten-nhanh> develop
```

Ví dụ: `git checkout -b feature/login-page develop` sẽ tạo nhánh mới tên feature/login-page từ develop và chuyển bạn sang nhánh đó. Bây giờ bạn có thể yên tâm lập trình trên nhánh feature này mà không ảnh hưởng đến mã của người khác cho đến khi bạn sẵn sàng hợp nhất.

Trong quá trình code, hãy commit thường xuyên vào nhánh của bạn (xem bước 4). Nếu làm việc kéo dài qua nhiều ngày, thi thoảng nên merge develop vào nhánh feature của bạn (hoặc rebase) để cập nhật những thay đổi mới của người khác vào nhánh của mình, tránh để khác biệt quá lớn. Làm điều này bằng cách trên nhánh feature, chạy:

```bash
git pull origin develop
# hoặc
git merge origin/develop
```

Điều này sẽ lấy code mới từ develop về và nhập vào nhánh hiện tại (feature) của bạn.

### 4. Thực hiện commit (lưu thay đổi cục bộ)

Sau khi hoàn thành một phần công việc (ví dụ code xong một hàm, fix xong một lỗi), hãy commit các thay đổi của bạn:

- Trước hết, xem trạng thái các file thay đổi bằng `git status`.
- Thêm (stage) các file cần thiết: `git add <file1> <file2>` (hoặc `git add .` để thêm tất cả thay đổi).
- Thực hiện commit với thông điệp rõ ràng:

```bash
git commit -m "<type>: <mô tả ngắn gọn>"
```

Hãy áp dụng quy tắc commit message đã nêu ở mục trên. Ví dụ:

```bash
git commit -m "feat: implement login API integration"
```

Nếu commit bao gồm nhiều thay đổi quan trọng, bạn nên viết message chi tiết hơn (sử dụng tùy chọn -m cho tiêu đề và mở editor để viết body) hoặc commit nhiều lần cho từng thay đổi nhỏ.

Luôn đảm bảo commit của bạn đúng nhánh. Dùng `git branch` để kiểm tra bạn đang ở nhánh nào trước khi commit, tránh commit nhầm vào develop. Nếu lỡ commit nhầm, có thể dùng git cherry-pick hoặc git reset để đưa commit đó sang nhánh đúng (liên hệ thành viên có kinh nghiệm để hỗ trợ nếu gặp trường hợp này).

### 5. Push nhánh lên GitHub

Sau khi có một hoặc nhiều commit trên nhánh feature cục bộ, hãy đẩy (push) nhánh đó lên GitHub để chia sẻ với nhóm và lưu trữ trên remote:

```bash
git push -u origin feature/login-page
```

Lần đầu push một nhánh mới, thêm `-u origin <branch-name>` để thiết lập nhánh theo dõi trên remote. Các lần sau chỉ cần `git push`.

Khi push, nếu có ai đó đã đẩy commit lên nhánh này trước bạn (hiếm khi xảy ra nếu mỗi người một nhánh riêng), Git sẽ báo cần pull trước. Thông thường với nhánh feature cá nhân, bạn sẽ là người duy nhất push. Nếu Git báo xung đột khi push, có thể do bạn chưa có commit mới nhất trên remote – hãy `git pull` rồi giải quyết conflict nếu có, sau đó push lại.

### 6. Tạo Pull Request (PR) để merge code

Khi bạn đã hoàn thành tính năng trên nhánh feature và đã push lên GitHub, bước tiếp theo là mở một Pull Request trên GitHub để đề xuất hợp nhất nhánh của bạn vào nhánh đích (develop hoặc main, tùy giai đoạn).

#### Cách tạo PR:

1. Truy cập trang repo trên GitHub, chuyển đến tab "Pull Requests". Nhấn nút "New Pull Request".
2. Chọn nhánh đích (base) là develop (hoặc nhánh mà bạn muốn merge vào) và nhánh nguồn (compare) là nhánh feature của bạn vừa push.

3. GitHub sẽ hiển thị các thay đổi (file diff) giữa hai nhánh. Thêm tiêu đề và mô tả cho PR:

    - Tiêu đề PR nên ngắn gọn nêu tính năng/bug fix (thường có thể giống hoặc mở rộng từ commit message chính). Ví dụ: "Thêm tính năng đăng nhập" hoặc "[Feature] Implement login screen UI".

    - Mô tả PR có thể ghi chi tiết những thay đổi đã làm, cách test, hoặc đính kèm ảnh chụp (nếu là giao diện). Nếu PR giải quyết một Issue nào đó, hãy đề cập từ khóa đóng issue như Closes #<issue_number> hoặc Fixes #<issue_number> để khi PR được merge thì issue đó tự động đóng

4. Gửi (Create) Pull Request. Lúc này mọi người trong nhóm có thể nhìn thấy PR của bạn.
Một khi PR được tạo và liên kết với issue (nếu có), GitHub sẽ hiển thị trong issue đó rằng có PR đang giải quyết nó. Điều này giúp mọi người biết công việc đang được thực hiện

### 7. Review code và thảo luận trên PR

Khi một PR được mở, các thành viên khác trong nhóm tham gia review (xem xét) code. Code review là bước quan trọng để đảm bảo chất lượng và chia sẻ kiến thức trong nhóm:

- Mọi người vào tab "Pull Requests", chọn PR cần review. Xem các thay đổi (Files changed), và sử dụng chức năng bình luận dòng để góp ý nếu thấy vấn đề hoặc cần cải thiện.

- Nếu PR liên quan tới phần code nhạy cảm hoặc phức tạp, hãy checkout nhánh PR về máy để chạy thử, test độc lập.

- Thảo luận: Sử dụng mục bình luận chung của PR để đặt câu hỏi hoặc trao đổi về giải pháp. Người tạo PR cần phản hồi các nhận xét, chỉnh sửa lại code nếu cần, và commit thêm vào nhánh feature (những commit mới này sẽ tự động cập nhật vào PR đang mở).

- Sau khi các vấn đề được giải quyết và mọi người đồng ý, một hoặc nhiều reviewer sẽ approve PR (trên GitHub có nút "Approve" khi review). Nhóm quy định ít nhất 1 hoặc 2 người phải duyệt trước khi merge, tùy độ quan trọng.

Việc review không nhằm "bắt lỗi" cá nhân mà để cùng nhau đảm bảo code tốt hơn. Thông qua review, các thành viên ít kinh nghiệm sẽ học hỏi được từ người khác, và ngược lại người review cũng hiểu rõ hơn phần code mới.

### 8. Merge PR và xóa nhánh đã dùng
Sau khi PR được chấp thuận, người chịu trách nhiệm (thường là người tạo PR hoặc người được giao nhiệm vụ) sẽ tiến hành merge. Trên giao diện GitHub của PR, nhấn nút "Merge Pull Request" (hoặc "Squash and merge"/"Rebase and merge" tùy chiến lược merge mà nhóm chọn, nhưng mặc định cứ "Create a merge commit" cho đơn giản). 

Khi merge thành công, mã nguồn từ nhánh feature đã được tích hợp vào nhánh đích (ví dụ `develop`). Lúc này, đóng PR (nếu GitHub chưa tự đóng) và xóa nhánh feature trên GitHub để repo sạch sẽ (có nút "Delete branch" sau khi merge). Lưu ý, xóa nhánh trên remote không ảnh hưởng đến bản local – trên máy bạn nhánh đó vẫn tồn tại. Bạn có thể chuyển sang nhánh `develop` và xóa nhánh feature local bằng lệnh:

```bash
git checkout develop
git pull origin develop    # cập nhật code mới vừa merge
git branch -d feature/login-page
```

Như vậy, vòng đời của một nhánh tính năng kết thúc khi nó đã được hợp nhất. Mỗi tính năng/bug fix lặp lại quy trình tương tự: tạo branch -> commit -> push -> tạo PR -> review -> merge.

**Lưu ý**: Luôn đảm bảo merge vào đúng nhánh đích theo mô hình đã thống nhất. Thông thường trong giai đoạn phát triển, mọi PR sẽ nhằm vào `develop`. Chỉ khi chuẩn bị phát hành mới merge `develop` vào `main` hoặc sử dụng PR nhằm vào `main` cho các hotfix cần thiết.

## Quản lý issue trên GitHub và liên kết với commit/PR

**Issue** trên GitHub dùng để theo dõi các yêu cầu tính năng, nhiệm vụ hoặc lỗi trong dự án. Quản lý công việc qua issue giúp nhóm phân chia rõ ai làm gì, và tình trạng mỗi công việc. Một số hướng dẫn cho việc dùng issue hiệu quả:

- **Tạo issue cho mỗi tính năng/bug:** Khi có một công việc cần làm (ví dụ thêm module đăng ký, sửa lỗi giao diện, viết tài liệu...), **nhóm trưởng** sẽ tạo một Issue trên GitHub, đặt tiêu đề và mô tả rõ ràng. Gắn nhãn (labels) phù hợp như `feature`(tính năng), `bug`, `documentation`, v.v., để phân loại. Cũng có thể gán issue cho người đảm nhiệm (Assignee) và gắn vào Milestone (mốc thời gian) nếu có kế hoạch.
- **Thảo luận trên issue:** Dùng phần bình luận của issue để mô tả chi tiết yêu cầu, hoặc thảo luận giải pháp trước khi làm. Ví dụ, trong issue "Thêm chức năng đăng nhập", nhóm có thể thảo luận cần những trường thông tin nào, giao diện ra sao, trước khi bắt đầu code.
- **Liên kết issue với branch và commit:** Như đã đề cập ở phần đặt tên branch, bạn có thể đưa số issue vào tên branch để dễ nhận biết. Quan trọng hơn, khi commit code liên quan, hãy đề cập đến issue đó trong thông điệp commit hoặc Pull Request. GitHub hỗ trợ **tự động liên kết và đóng issue** thông qua từ khóa đặc biệt trong commit/PR:

    - Thêm cụm từ `Fixes #<số issue>` hoặc `Closes #<số issue>` trong message. Ví dụ: `git commit -m "fix: handle null pointer in login (Closes #34)"`. Khi nhánh này được merge vào nhánh chính, issue #34 sẽ tự động chuyển sang trạng thái đóng

    - Tương tự, trong phần mô tả của Pull Request, ghi `Resolve #<issue>` cũng sẽ liên kết PR với issue. Khi PR được merge, issue sẽ đóng

    - Lưu ý: Từ khóa phải đi cùng ký hiệu `#` và số issue chính xác, cũng như PR phải merge vào nhánh mặc định (thường là `main`) hoặc default branch trong cài đặt dự án thì GitHub mới tự động đóng issue. Trường hợp merge vào `develop`, issue có thể không tự đóng, khi đó người quản lý cần đóng thủ công khi tính năng đã hoàn thành và đưa vào bản phát hành.

- **Theo dõi tiến độ qua issue:** Mỗi issue có trạng thái mở/đóng. Nhóm trưởng sẽ thường xuyên cập nhật trạng thái: khi bắt đầu làm, assign issue cho người làm và có thể đặt trạng thái "In progress" (nếu dùng project board của GitHub). Khi code đã merge xong, đảm bảo issue được đóng. Có thể sử dụng GitHub Projects (bảng Kanban) để quản lý các issue theo các cột To do / In progress / Done để dễ theo dõi toàn cảnh.

- **Gắn issue vào commit/PR giúp minh bạch:** Tất cả commit và PR liên quan đến một issue sẽ hiển thị trong timeline của issue đó. Điều này rất hữu ích khi làm báo cáo: chỉ cần mở issue, bạn sẽ thấy lịch sử ai đã làm gì, commit nào đã giải quyết nó. Ví dụ, issue #12 "Thiết kế CSDL" có thể liệt kê các commit và PR liên quan đến việc thiết kế và tạo migration cho CSDL, v.v.

Tóm lại, hãy coi mỗi issue như một nhiệm vụ độc lập. Quy trình lý tưởng: Tạo issue -> Thảo luận giải pháp -> Tạo branch cho issue -> Commit code, ghi chú issue trong commit -> Tạo PR, review -> Merge -> Issue tự động đóng. Cách làm này đảm bảo không nhiệm vụ nào bị bỏ sót và mọi người đều nắm được trạng thái công việc của nhau.