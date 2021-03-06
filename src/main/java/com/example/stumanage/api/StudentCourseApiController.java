package com.example.stumanage.api;

import com.example.stumanage.common.ResponseResult;
import com.example.stumanage.vo.TbAdminVo;
import com.github.pagehelper.PageInfo;
import com.example.stumanage.service.StudentCourseService;
import com.example.stumanage.service.StudentSchoolService;
import com.example.stumanage.vo.TbStudentCourseVo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生课程api
 * @author: zhangzengke
 * @date: 2018/2/1
 **/

@RequestMapping("api/course")
@RestController
public class StudentCourseApiController {

    @Resource
    private StudentCourseService studentCourseService;

    @Resource
    private StudentSchoolService studentSchoolService;

    @GetMapping("/findById")
    public ResponseResult find(@RequestParam Integer id) {
        studentCourseService.findById(id);

        return new ResponseResult();
    }
    @GetMapping("/del/{id}")
    public ResponseResult del(@PathVariable Integer id){
        studentCourseService.del(id);

        return new ResponseResult();
    }

    /**
     * 根据学号查找学生的课程信息分页
     */
    @GetMapping("/findAll")
    public ResponseResult<PageInfo<TbStudentCourseVo>> findAll(@RequestParam String stuId,@RequestParam Integer page,@RequestParam Integer pageSize) {
        TbStudentCourseVo tbStudentCourseVo = new TbStudentCourseVo();
        tbStudentCourseVo.setStuId(stuId);
        tbStudentCourseVo.setPage(page);
        tbStudentCourseVo.setPageSize(pageSize);

        List<TbStudentCourseVo> tbStudentCourseVos = studentCourseService.findByStuId(tbStudentCourseVo);
        return new ResponseResult<>(new PageInfo<>(tbStudentCourseVos));
    }

    /**
     * 为该学生添加课程
     * @param
     * @return
     */
    @GetMapping("/save")
    public ResponseResult save(@RequestParam String courseName ,@RequestParam String teacher,@RequestParam  String stuId,HttpServletRequest request){
        TbStudentCourseVo tbStudentCourseVo = new TbStudentCourseVo();
        tbStudentCourseVo.setStuId(stuId);
        tbStudentCourseVo.setCourseName(courseName);
        tbStudentCourseVo.setTeacher(teacher);
        TbAdminVo tbAdminVo= (TbAdminVo)request.getSession().getAttribute("tbAdmin");
        tbStudentCourseVo.setModifiedBy(tbAdminVo.getNickName());
        tbStudentCourseVo.setCreatedBy(tbAdminVo.getNickName());
        studentCourseService.saveCourse(tbStudentCourseVo);

        return new ResponseResult();
    }
    /**
     * 为该学生修改课程信息可以添加成绩和成绩分析
     * @param tbStudentCourseVo
     * @return
     */
    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody TbStudentCourseVo tbStudentCourseVo, HttpServletRequest request){
        TbAdminVo tbAdminVo= (TbAdminVo)request.getSession().getAttribute("tbAdmin");
        tbStudentCourseVo.setCreatedBy(tbAdminVo.getNickName());
        tbStudentCourseVo.setModifiedBy(tbAdminVo.getNickName());
        studentCourseService.editCourse(tbStudentCourseVo);

        return new ResponseResult();
    }
}
