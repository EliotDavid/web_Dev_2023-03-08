package com.koreait.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.board.common.constant.ResponseMessage;
import com.koreait.board.dto.request.humanResource.PatchHumanResourceRequestDto;
import com.koreait.board.dto.request.humanResource.PostHumanResourceRequestDto;
import com.koreait.board.dto.response.ResponseDto;
import com.koreait.board.dto.response.humanResource.GetHumanResourceResponseDto;
import com.koreait.board.dto.response.humanResource.PatchHumanResourceResponseDto;
import com.koreait.board.dto.response.humanResource.PostHumanResourceResponseDto;
import com.koreait.board.entity.EmployeeEntity;
import com.koreait.board.repository.DepartmentRepository;
import com.koreait.board.repository.EmployeeRepository;

@Service
public class HumanResourceService {
    
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private DepartmentRepository departmentRepository;

    public ResponseDto<PostHumanResourceResponseDto> postHumanResource(PostHumanResourceRequestDto dto) {
        
        PostHumanResourceResponseDto data = null;

        String telNumber = dto.getTelNumber();
        String departmentCode = dto.getDepartment();

        try {
            
            boolean hasTelNumber = employeeRepository.existsByTelNumber(telNumber);
            if (hasTelNumber) return ResponseDto.setFail(ResponseMessage.EXIST_TELEPHONE_NUMBER);
            
            if (departmentCode != null) {
                boolean hasDepartment = departmentRepository.existsById(departmentCode);
                if (!hasDepartment) return ResponseDto.setFail(ResponseMessage.NOT_EXIST_DEPARTMENT_CODE);
            }

            EmployeeEntity employeeEntity = new EmployeeEntity(dto);
            employeeRepository.save(employeeEntity);

            data = new PostHumanResourceResponseDto(employeeEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.setFail(ResponseMessage.DATABASE_ERROR);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

    }

    public ResponseDto<GetHumanResourceResponseDto> getHumanResource(int employeeNumber) {

        GetHumanResourceResponseDto data = null;

        try {
            // boolean hasEmployee = employeeRepository.existsById(employeeNumber);
            // if (!hasEmployee) return ResponseDto.setFail("존재 X");
            // EmployeeEntity employeeEntity = employeeRepository.findById(employeeNumber).get();

            EmployeeEntity employeeEntity = employeeRepository.findByEmployeeNumber(employeeNumber);
            if (employeeEntity == null) return ResponseDto.setFail(ResponseMessage.NOT_EXIST_EMPLOYEE_NUMBER);

            data = new GetHumanResourceResponseDto(employeeEntity);
        
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.setFail(ResponseMessage.DATABASE_ERROR);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

    }

    // 사번에 해당하는 사원이 존재하지 않을 때
    public ResponseDto<PatchHumanResourceResponseDto> patchHumanResource(PatchHumanResourceRequestDto dto){
        PatchHumanResourceResponseDto data = null;
        int employeeNumber = dto.getEmployeeNumber(); 
        String departmentCode = dto.getDepartment();

        try {
            // 사원번호를 가졌다면 검색을 해봐야겠지
            boolean hasEmployee = employeeRepository.existsById(employeeNumber);
            // 그런데 만약에 사원번호가 존재하지 않는다면 수정하지 못하도록 해줘야함
            if(!hasEmployee) return ResponseDto.setFail(ResponseMessage.NOT_EXIST_EMPLOYEE_NUMBER);
            
            if(departmentCode != null){
                    // 부서코드가 있는지 없는지 확인
                boolean hasDepartment = departmentRepository.existsById(departmentCode);
                if(!hasDepartment) return ResponseDto.setFail(ResponseMessage.NOT_EXIST_DEPARTMENT_CODE);
            }
        
            EmployeeEntity employeeEntity = new EmployeeEntity(dto); // 엔티티로 패킹해준 다음에 
            employeeRepository.save(employeeEntity); // 그 엔티티를 레파지토리로 이동 -> 엔티티매니저로 넣는 단계

            data = new PatchHumanResourceResponseDto(employeeEntity);

        } catch (Exception exception) { // 아 이게 exception(예외라는것)이기 때문에 데이터베이스 관련 문제도 발생할 수 있으니 그래서 아래에서 데이터베이스 문제라는 문구를 출력해주는군 
            exception.printStackTrace();
            // 해당하는 사원이 존재하지 않는다면 : 해당하는 사원이 존재하지 않는데 데이터베이스 에러를 날려준다? 맞나 이게? 해당하는 사원이 없는데 왜 데이터베이스 에러를 날려주지?
            return ResponseDto.setFail(ResponseMessage.DATABASE_ERROR);
        }

        // 정상처리가 되었다면 : 해당하는 사원이 존재한다면 
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

}
