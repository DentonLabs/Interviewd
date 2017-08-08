using System.Threading.Tasks;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Microsoft.AspNetCore.Mvc;

namespace Interviewd.Controllers
{
    [Route("templates")]
    public class InterviewTemplatesController : Controller
    {
        private readonly IInterviewTemplateManager _InterviewTemplateManager;

        public InterviewTemplatesController(
            IInterviewTemplateManager interviewTemplateManager)
        {
            _InterviewTemplateManager = interviewTemplateManager;
        }

        [HttpPost]
        public async Task<InterviewTemplateDto> PostInterviewTemplate([FromBody] InterviewTemplateDto interviewTemplateDto)
        {
            return await _InterviewTemplateManager.CreateInterviewTemplate(interviewTemplateDto);
        }
    }
}