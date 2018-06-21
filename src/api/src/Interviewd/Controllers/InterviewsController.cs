using System.Collections.Generic;
using System.Threading.Tasks;
using Interviewd.Application;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Interviewd.Controllers
{
    [Route("interviews")]
    [Authorize]
    public class InterviewsController : Controller
    {
        private readonly IInterviewManager _InterviewManager;

        public InterviewsController(IInterviewManager interiewManager)
        {
            _InterviewManager = interiewManager;
        }

        [HttpPost]
        public async Task<InterviewDto> PostInterview([FromQuery]string templateId, [FromQuery]string candidateId)
        {
            return await _InterviewManager.CreateInterview(templateId, candidateId);
        }

        [HttpGet]
        [Route("{id}")]
        public async Task<InterviewDto> GetInterview([FromRoute] string id)
        {
            return await _InterviewManager.GetInterview(id);
        }

        [HttpGet]
        public async Task<IEnumerable<InterviewDto>> GetInterviews()
        {
            return await _InterviewManager.GetInterviews();
        }
    }
}