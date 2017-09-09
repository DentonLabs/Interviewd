using System.Threading.Tasks;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Microsoft.AspNetCore.Mvc;

namespace Interviewd.Controllers
{
    [Route("interviews")]
    public class InterviewsController : Controller
    {
        private readonly IInterviewManager _InterviewManager;

        public InterviewsController(IInterviewManager interiewManager)
        {
            _InterviewManager = interiewManager;
        }

        public async Task<InterviewDto> PostInterview([FromQuery]string templateId)
        {
            return await _InterviewManager.CreateInterview(templateId);
        }
    }
}