package violet.action.common.service;

import violet.action.common.proto_gen.action.*;

public interface UserService {
    LoginResponse login(LoginRequest req);

    RegisterResponse register(RegisterRequest req);

    GetUserInfosResponse getUserInfos(GetUserInfosRequest req);

    SearchUsersResponse searchUsers(SearchUsersRequest req);

    ReportClickResponse reportClick(ReportClickRequest req);
}
